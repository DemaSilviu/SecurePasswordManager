package com.example.securepasswordmanager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;


public class BTService {
    private static final String TAG = "BluetoothConnectionServ";

    private static final String appName = "MYAPP";

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    private ConnectedThread mConnectedThread;

    public BTService(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }


    // This thread runs while listening for incoming connections.It runs until a connection is accepted or until its canceled

    private class AcceptThread extends Thread {

        // Server socket(local)
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try{
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);

            }catch (IOException e){
            }

            mmServerSocket = tmp;
        }
// run method after acceptthread
        public void run(){

            BluetoothSocket socket = null;

            try{
                // This is a blocking call.Will return only on a successfull connection or an exception
                socket = mmServerSocket.accept();
            }catch (IOException e){
                e.printStackTrace();
            }

            if(socket != null){
                connected(socket,mmDevice);
            }

        }
        // close the server
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //This thread runs while attempting to make an outgoing connection with a device. It runs straight through; the connection eithersucceeds or fails.
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        //constructor
        public ConnectThread(BluetoothDevice device, UUID uuid) {
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run(){
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the given mmDevice
            try {
                tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmSocket = tmp;

            // Always cancel discovery otherwise will slow the connection
            mBluetoothAdapter.cancelDiscovery();

            // Make a connection to the mmSocket(BluetoothSocket)
            try {
                mmSocket.connect();

            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e1)
                {
                    e.printStackTrace();
                }
            }
            connected(mmSocket,mmDevice);
        }
        //close the socket
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();            }
        }
    }



    //Start the chat service.Start AcceptThread to begin asession in listening (server) mode. Called by the Activity onResume()
    public synchronized void start() {

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    //AcceptThread starts and wait for a connection.Then ConnectThread starts and attempts to make a connection with the other devices(Accept Thread)
    public void startClient(BluetoothDevice device,UUID uuid){
        //initprogress dialog to show the user that Bluetooth is connecting
        mProgressDialog = ProgressDialog.show(mContext,"Connecting Bluetooth"
                ,"Please Wait...",true);

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    // ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and receiving incoming data through input/output streams respectively.
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try{
                mProgressDialog.dismiss();
            }catch (NullPointerException e){
                e.printStackTrace();
            }


            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[1024];  // buffer store for the stream

            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                // Read from the InputStream
                try {
                    bytes = mmInStream.read(buffer);
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.d(TAG, "InputStream: " + incomingMessage);
                } catch (IOException e)
                {
                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage() );
                    break;
                }
            }
        }

        //Call this from the main activity to send data to the remote device
        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to outputstream: " + text);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to output stream. " + e.getMessage() );
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice)
    {
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

 // its same signature as above but we need this in order to be able to call it from other classes. This method will call write method from BTService ConnectThread
    public void write(byte[] out)
    {
        // Create temporary object
        ConnectedThread r;
        //perform the write
        mConnectedThread.write(out);
    }

}
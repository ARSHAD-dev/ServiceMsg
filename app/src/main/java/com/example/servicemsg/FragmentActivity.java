package com.example.servicemsg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Created by Arshad.....
 */

public class FragmentActivity extends Fragment {
    Messenger mService;
    boolean mBound = false;
    final String TAG = "FragmentActivity";

    public FragmentActivity() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), MyService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_activity, container, false);
        myview.findViewById(R.id.btn_gn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    // Create and send a message to the service, using a supported 'what' value
                    Message msg = Message.obtain(null, MyService.HELLOMSG, 0, 0);
                    try {
                        mService.send(msg);
                        Log.v(TAG, "Message sent.");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.v(TAG, "Bound is false");
                }

            }
        });
        return myview;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
            Log.v(TAG, "connected is true");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mBound = false;
            Log.v(TAG, "Disconnected!");
        }
    };
}



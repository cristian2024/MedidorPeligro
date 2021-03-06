package com.redes.medidor.ui.vehiculo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redes.medidor.ui.Adapters.ListaBluetoothAdapter;
import com.redes.medidor.R;
import com.redes.medidor.ViewModel.DatosViewModel;


import java.util.ArrayList;

public class VehiculosFragment extends Fragment {

    //Manejo del recyclerView
    ListaBluetoothAdapter adapter;
    RecyclerView recycler;

    private DatosViewModel datosViewModel;

    public static VehiculosFragment newInstance() {
        return new VehiculosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.vehiculos_fragment, container, false);
        //iniciando variables
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datosViewModel = new ViewModelProvider(requireActivity()).get(DatosViewModel.class);



        //iniciando recycler
        iniciarRecycler();


        //Generacion de observadores para este fragmento
        observadores();

        // TODO: Use the ViewModel
    }

    private void iniciarRecycler() {
        //Creando el adapter para el recycler view con los datos para el constructor definidos en la clase
        adapter=new ListaBluetoothAdapter(this.getActivity(), datosViewModel);
        recycler.setAdapter(adapter);
    }

    private void init(View view) {
        //iniciando los datos del recycler
        recycler=view.findViewById(R.id.recyclerView);
        //definiendo el manager del recycler
        recycler.setLayoutManager(new LinearLayoutManager(VehiculosFragment.this.getContext()));

    }


    //Algunos observadores que iran en este fragment
    private void observadores(){
        //Revisando cuando se cambien los datos de dispositivos disponibles
        datosViewModel.getDispositivos().observe(getViewLifecycleOwner(), new Observer<ArrayList<BluetoothDevice>>() {
            @Override
            public void onChanged(ArrayList<BluetoothDevice> bluetoothDevices) {
                if(bluetoothDevices==null){
                    Log.i("Mensaje", "No lo esta cargando");

                }
                adapter.notifyDataSetChanged();

            }
        });
    }


}
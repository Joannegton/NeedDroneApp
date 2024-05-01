package com.example.needdroneapp.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjetoViewModel extends ViewModel {
    private MutableLiveData<String> statusProjeto;

    public ProjetoViewModel() {
        statusProjeto = new MutableLiveData<>();
    }

    public LiveData<String> getStatusProjeto() {
        return statusProjeto;
    }

    public void atualizarStatusProjeto(String status) {
        statusProjeto.setValue(status);
    }
}

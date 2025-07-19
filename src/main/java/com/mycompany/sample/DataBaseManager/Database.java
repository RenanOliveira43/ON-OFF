package com.mycompany.sample.DataBaseManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.attach.util.Platform;
import com.mycompany.sample.AppManager.Computer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"computers"})
public class Database {
    @JsonProperty("computers")
    private List<Computer> users = new ArrayList<>();
    
    private final File file;
    
    public Database(boolean load) throws FileNotFoundException {
        if (Platform.isAndroid()) {
            File privateStorage = StorageService.create()
                .flatMap(StorageService::getPrivateStorage)
                .orElseThrow(() -> new FileNotFoundException("Could not access private storage."));
    
            file = new File(privateStorage, "database.json");
        } else {
            String currentDirectory = System.getProperty("user.dir");
            File dataDirectory = new File(currentDirectory, "data");
    
            if (!dataDirectory.exists()) {
                dataDirectory.mkdirs();
            }
    
            file = new File(dataDirectory, "database.json");
        }
    
        if (load) {
            this.load();
        }
    }

    public Database() {
        this.file = null;
    }

    public List<Computer> getComputers() {
        return users;
    }

    public void insert(Object object) {
        try{
            this.users.add((Computer) object);
        } catch (ClassCastException e) {
            System.err.println("Erro ao adicionar objeto: " + e.getMessage());
        }

        this.save();
    }

    private <T> void update(T newItem, List<T> data) {
        for (int i = 0; i < data.size(); i++) {
            Object item = data.get(i);

            if (item.equals(newItem)) {
                data.set(i, newItem);
            }
        }
    }

    public void update(Object object) {
        try {
            this.update((Computer) object, this.users);
        } catch (ClassCastException e) {
            System.err.println("Erro ao atualizar objeto: " + e.getMessage());
        }
 
        this.save();
    }

    private void save() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(this.file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        if (file.exists()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Database db = objectMapper.readValue(this.file, Database.class);
                this.users = db.getComputers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
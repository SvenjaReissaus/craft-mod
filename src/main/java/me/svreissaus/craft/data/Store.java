package me.svreissaus.craft.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Store<TStore> {
    private final Store<TStore>.StoreConnector<TStore> _connector;
    private TStore store;

    public Store(TStore store, File folder, String file) {
        this._connector = new StoreConnector<TStore>(store, folder, file);
        this.store = store;
    }

    public Store(TStore store, File folder) {
        this(store, folder, store.getClass().getSimpleName().toLowerCase());
    }

    public TStore store() {
        return this.store;
    }

    public TStore createStore(Class<TStore> store) {
        return this.store = this._connector.loadSave(store);
    }

    public TStore loadStore(Class<TStore> store) {
        return this.store = this._connector.load(store);
    }

    public TStore saveStore(TStore store) {
        return this.store = this._connector.save(store);
    }

    public TStore loadSave(Class<TStore> clazz) {
        return this.store = this._connector.loadSave(clazz);
    }

    public TStore saveLoad(TStore store) {
        return this.store = this._connector.save(store);
    }

    private final class StoreConnector<TStoreEntity> {
        private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                .enableComplexMapKeySerialization().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
                .excludeFieldsWithoutExposeAnnotation().create();
        private final File file;
        private TStoreEntity entity;

        public StoreConnector(TStoreEntity entity, File folder, String name) {
            if (!folder.exists())
                folder.mkdirs();
            this.file = new File(folder, name + ".json");
            this.entity = entity;
        }

        public TStoreEntity loadSave(Class<TStoreEntity> store) {
            try {
                if (this.file.exists()) {
                    return this.load(store);
                }
                return this.save(this.entity);
            } catch (Exception ignore) {
            }
            return this.entity;
        }

        public TStoreEntity load(Class<TStoreEntity> store) {
            try {
                String json = this.read();
                return this.gson.fromJson(json, store);
            } catch (Exception ignore) {
            }
            return this.entity;
        }

        public TStoreEntity save(TStoreEntity latest) {
            try {
                this.write(this.gson.toJson(latest));
            } catch (Exception ignore) {
            }
            return this.entity;
        }

        /**
         * Convenience function for writing a string to a file.
         */
        private void write(String content) throws IOException {
            FileOutputStream out = new FileOutputStream(file);
            out.write(content.getBytes(StandardCharsets.UTF_8));
            out.close();
        }

        /**
         * Convenience function for reading a file as a string.
         */
        private String read() throws IOException {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));
            String ret = new String(new byte[0], StandardCharsets.UTF_8);
            String line;
            while ((line = in.readLine()) != null)
                ret += line;
            in.close();
            return ret;
        }
    }
}
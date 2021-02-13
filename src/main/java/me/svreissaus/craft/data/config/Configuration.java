package me.svreissaus.craft.data.config;

import java.io.File;

import me.svreissaus.craft.data.Store;

public final class Configuration {
    private Store<Configuration> _store;

    public Configuration store;

    public Configuration(File folder) {
        this._store = new Store<Configuration>(this, folder);
        this.store = _store.createStore(Configuration.class);
    }

    public Configuration load() {
        this.store = this._store.loadStore(Configuration.class);
        return this.store;
    }

    public Configuration save() {
        return this._store.saveStore(store);
    }
}

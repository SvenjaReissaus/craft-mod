package me.svreissaus.craft.data;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class CraftStore {
    private Store<CraftStore> _store;
    public CraftStore store;

    public CraftStore(File folder) {
        this._store = new Store<CraftStore>(this, folder);
        this.store = _store.createStore(CraftStore.class);
    }

    public CraftStore load() {
        this.store = this._store.loadStore(CraftStore.class);
        return this.store;
    }

    public CraftStore save() {
        return this._store.saveStore(store);
    }

    public transient HashMap<UUID, UUID> tpateleports = new HashMap<UUID, UUID>();
    public transient HashMap<UUID, UUID> tpahereteleports = new HashMap<UUID, UUID>();
}

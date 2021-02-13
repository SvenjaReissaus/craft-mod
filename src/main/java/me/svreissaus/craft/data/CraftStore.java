package me.svreissaus.craft.data;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import me.svreissaus.craft.data._data.PlayerHome;
import me.svreissaus.craft.data._data.PlayerTeleport;

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

    public transient HashMap<UUID, HashSet<PlayerTeleport>> playerTeleports = new HashMap<UUID, HashSet<PlayerTeleport>>();
    public HashMap<UUID, PlayerHome> homes = new HashMap<UUID, PlayerHome>();
}

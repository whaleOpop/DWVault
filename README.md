# DWAPI
Double Whale API core plugin

## Warn
This is a snapshots branch (nightly, beta, develop, e.t.c).
All the bugs and beasts live here!

## Why though?
After 2 month of questioning and planing, we agreed to move some methods to core plugin.
This disconnects data collection, saving, loading and managing from actual data usage and generation via out other plugins.
It benefits from using more than one DW plugin, two for now, actually.

## How do I use it?
* Firstly, implement a hook for your plugin in Hooks package.
* Then add tasks and modify task execution for any non-standart (not time based) tasks in Tasks package. This step is optional.
* Define your data models in DataModel package. All data models must implement Bukkit ConfigurationSerializable interface for saving.
* After that define your plugin's vault. Vault is another serializable class-wrapper. It handles list serialization of data models.
* Import DWAPI and defined plugin Hooks in your project.
* Profit!

NetBeans MongoDB
================

A [NetBeans](http://netbeans.org) plugin for accessing [MongoDB](http://mongodb.org). It adds
a node to the Services tab. Right click it to add connections.

Available main features:
 * Connect to MongoDB using mongo standard uri
 * Browse/Create/Drop databases
 * Browse/Create/Rename/Delete collections
 * Query documents (json criteria/projection/sort can be specified)
 * Add/Edit/Delete documents as json
 * Import/Export json
 * Use some mongo [native tools](https://github.com/le-yams/netbeans-mongodb/wiki/MongoNativeTools) as mongo shell, mongodump, mongorestore, etc. (mongo bin folder path must be configured in options)

See [release notes](https://github.com/le-yams/netbeans-mongodb/wiki/ReleaseNotes) for more informations.

![NetBeans MongoDB Plugin Screen Shot](https://raw.githubusercontent.com/le-yams/netbeans-mongodb/master/screenshots/screenshot-medium-1.png "NetBeans MongoDB Plugin Screen Shot")


Get and Install
---------------

NBMongo is now directly available through the NetBeans Plugin Portal Update Center. 
In NetBeans, go to **_Tools > Plugins_** and on the **_Available Plugins_** tab search for _NBMongo_. Select the plugin and hit the _Install_ button.
See also [NBMongo Plugin Portal page](http://plugins.netbeans.org/plugin/52638).

If you want to try the development version, as it's a Maven project built using the NBM Maven Plugin, just check out and build.
In NetBeans, install using Tools | Plugins on the Downloaded tab.


License
-------
MIT license

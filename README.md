# A Maven Archetype for JEE7 Application: jee7-sandbox

*jee7-sandbox* provides a sandbox skeleton for developing JEE7
applications. You may find some samples and good practices from common
components from the JEE7 and related specifications.

This archetype targets the Wildfly 8 application server.

## Installing the Archetype

First, create an archetype from this project:

    mvn archetype:create-from-project

This will create an archetype under `target/generated-sources/archetype`.

Next, change into this directory:

    cd target/generated-sources/archetype

Now, you can no install this archetype:

    mvn install

The archetype is installed under `~/.m2/repository/com/telosoft/archetypes/`.

## Creating a new Project from the Archetype

If you want to create a new project using this archetype, create and
change to any arbitrary directory you like:

    mkdir /tmp/my-new-jee7-project
    cd /tmp/my-new-jee7-project

Since the archetype is installed (like in the previous section)
locally and it is not yet available in the central Maven repositories,
create the new project using this archetype using the local archetype
catalog as follows:

    mvn archetype:generate -DarchetypeCatalog=local

When the command runs, you will be prompted for the archetype you want
to use. Locate and choose this archetype.

## Credits

Copyright (c) 2013-2014 Vehbi Sinan Tunalioglu.

This archetype is a *Free and Open Source Software*. It is licensed
under the Apache License 2.0.

# Steam Tools

Tools for checking Team Fortress 2 (TF2) players for VAC and community server bans. Know who you're playing with!

![](https://github.com/fooberticus/SteamTools/blob/master/img/all-players.png)

# Setup

You can download a Zip or MSI installer for Windows from the releases section. Either install using the MSI, or unzip 
where you wish to install. Run the .exe and the program will start.

Alternatively, you can clone this git project and with Maven installed, run `mvn exec:java` in the project root.

The first time you run the program, you'll be presented with the Settings menu:

![](https://github.com/fooberticus/SteamTools/blob/master/img/settings.png)

The first thing you need are API keys for the Steam API and SteamHistory.net API.

You can get a Steam API key here:

https://steamcommunity.com/dev/apikey

And for SteamHistory.net, you can login to the site with your Steam account and then get an API key here:

https://steamhistory.net/api

Once you've copied and pasted the API keys into the appropriate fields, you can save and then close the settings menu.
You're ready to use Steam Tools!

You can also increase/decrease the global font size and change the UI theme from this menu. Play around with the settings
until you find a look and feel that you like best.

# Usage

This is the main window:

![](https://github.com/fooberticus/SteamTools/blob/master/img/empty-main.png)

In the text field of this window, you can input any number of Steam32 IDs -- Steam IDs that look like [U:1:12345678].
The most common way to use this tool is to open the console while playing TF2, type in the command `status`, then copy the
text that comes out and paste it into this text box (you can **right-click to paste** into this window to save time):

![](https://github.com/fooberticus/SteamTools/blob/master/img/pasted-main.png)

The text you copy and paste doesn't have to be precise, just make sure it contains the Steam32 IDs that you want to research.

Next, click the Check Users button and wait a second or two, and the results of the Steam and community ban check will come back.

If any of the checked Steam IDs have a VAC or Steam Game ban associated, the Steam Bans tab will appear with their name and
some info about their bans shown. You can double-click a row of player data to open their Steam profile page and have a closer look:

![](https://github.com/fooberticus/SteamTools/blob/master/img/steam-bans.png)

Likewise, if any Steam IDs have community server bans, the Community Bans tab will be present with the player's name and ban summary.
Again, you can double-click a row of player data to open their page on SteamHistory.net and see more details about all their community bans:

![](https://github.com/fooberticus/SteamTools/blob/master/img/community-bans.png)

Finally, on the All Players tab you'll see a summary of all players on the server, whether they have a ban or not. Double click
their row to open their Steam Profile to have a closer look:

![](https://github.com/fooberticus/SteamTools/blob/master/img/all-players.png)

And that's it for now. More features are being added, but as of now this is a handy way to quickly find out if you are playing
with anyone who has a known record of cheating in TF2!

# Build and package the project

If you're a Java developer, you can build the project with Maven:

`mvn clean package`

If you want to use `jpackage` to make an MSI and package up the JVM with a native executable, use the scripts found in the "jpackage"
folder in the root of the project. Just copy the *jar-with-dependencies.jar into the `jpackage\jars` folder, and run the 
batch script named `package-windows.bat` to create the MSI and Package with executable.
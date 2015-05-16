Stijn Van Crombrugge

To make it work you do have to go to the cmd prompt first and direct to the file of the project using => cd "fileurl"
Then you write "rmiregistry" in the command.
Then you open another cmd prompt while the first is still open and there you direct to the RMI file in the project using => cd "url of the rmifile" and there you write javac RMI.Client and javac RMI.Server to start server and client
When its working you will be able to play the multiplayer mode of the program, otherwise it will produce errors
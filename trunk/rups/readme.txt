*********************************
*        Building Rups          *
*********************************

Running a maven build without profile will just build the jar.
Running the build with profiles:
profile name      build actions
all               generate jar, jar with dependencies in it, sources jar, javadoc jar
exe               create a windows exe to run Rups
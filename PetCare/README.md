
For generating apis and dtos from swagger file (located in src/main/resources):
*mvn clean generate-sources -Dmaven_exec_skip=false*
PLEASE CHECK THAT THE FILES HAVE BEEN MODIFIED CORRECTLY

*mvn antrun:run@copy*
This command is to move the generated apis and dtos to src file AND DELETE THE TARGET FILES
******************************************

You can also do them both like this:

*mvn clean generate-sources -Dmaven_exec_skip=false antrun:run@copy*

import pl.blueenergy.document.DocumentDao;

public class Main {
	
	public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, InstantiationException {

		DocumentDao documentDao = new DocumentDao();
		ProgrammerService programmerService = new ProgrammerService();
		programmerService.execute(documentDao);

	}
}

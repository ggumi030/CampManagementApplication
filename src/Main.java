import camp.CampManagementApplication;

public class Main {
    public static void main(String[] args) {
        CampManagementApplication campManagementApplication = new CampManagementApplication();
        try {
            campManagementApplication.displayMainView();
        } catch (InterruptedException e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }
}
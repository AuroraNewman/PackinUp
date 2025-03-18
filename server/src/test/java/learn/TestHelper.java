package learn;

import learn.models.User;

public class TestHelper {
    public static int badId = -999;
    public static int goodId = 1;
    public static String tooLongUsername = "When you know who you are; when your mission is clear and you burn with the inner fire of unbreakable will, no cold can touch your heart, no deluge can dampen your purpose. You know that you are alive.";
    public static String goodUsername = "TestUser";
    public static String badFormatEmail = "testuser@";
    public static String goodEmail = "testemail@testing.com";
    public static String tooLongEmail = "TaumatawhakatangihangakoauauotamateaturipukakapikimaungahoronukupokaiwhenuakitanatahuIsARealPlaceInNewZealand@testing.com";
    public static String badFormatPasswordNoCaps = "password1!";
    public static String badFormatPasswordNoSpecial = "Password1";
    public static String badFormatPasswordNoNumber = "Password!";
    public static String badFormatPasswordNoLowers = "PASSWORD1!";
    public static String badFormatPasswordShort = "P1!";
    public static String goodPassword = "Password1!";

    public static User goodUser = new User(goodId, goodUsername, goodEmail, goodPassword);
    public static User existingUser = new User(1, "Bernie", "Bernie@rubiber.com", "verygoodpassword");
}

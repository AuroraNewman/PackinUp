package learn;

import learn.data_transfer_objects.IncomingTemplate;
import learn.models.*;

public class TestHelper {
    public static int badId = -999;
    public static int goodId = 1;
    public static String goodVarCharString = "TestString";
    public static String tooLongVarCharString = "When you know who you are; when your mission is clear and you burn with the inner fire of unbreakable will, no cold can touch your heart, no deluge can dampen your purpose. You know that you are alive.";
// User data
    public static String goodUsername = "TestUser";
    public static String badFormatEmail = "testuser@";
    public static String goodEmail = "testemail@testing.com";
    public static String tooLongEmail = "TaumatawhakatangihangakoauauotamateaturipukakapikimaungahoronukupokaiwhenuakitanatahuIsARealPlaceInNewZealand@testing.com";
    public static String badFormatPasswordNoCaps = "password1!";
    public static String badFormatPasswordNoSpecial = "Password1";
    public static String badFormatPasswordNoNumber = "Password!";
    public static String badFormatPasswordNoLowers = "PASSWORD1!";
    public static String badFormatPasswordShort = "Pa1!";
    public static String goodPassword = "Password1!";

    public static User existingUser = new User(1, "Bernie", "Bernie@rubiber.com", "veryg00dPassword!");
    public static TripType existingTripType = new TripType(1, "General", "Not specified");
    public static Template existingTemplate = new Template(1, "General", "Not specified", existingTripType, existingUser);
    public static Category existingCategory = new Category(1, "Weather", "Blue");
    public static Item existingItem = new Item(goodId, "Gloves", existingUser, existingCategory);

    public static User makeTestUser() {
        return new User(goodId, goodUsername, goodEmail, goodPassword);
    }
    public static TripType makeTestTripType() {
        return new TripType(goodId, goodVarCharString, goodVarCharString);
    }
    public static Template makeTestTemplate() {
        return new Template(goodId, goodVarCharString, goodVarCharString, existingTripType, existingUser);
    }
    public static IncomingTemplate makeTestAddTemplate(){
        return new IncomingTemplate(goodVarCharString, goodVarCharString, existingTripType.getTripTypeId());
    }
    public static Item makeTestItem() {
        return new Item(goodId, goodVarCharString, existingUser, existingCategory);
    }
    public static Category makeTestCategory() {
        return new Category(goodId, goodVarCharString, goodVarCharString);
    }

    public static TemplateItem makeTestTemplateItem() {
        return new TemplateItem(2, goodId, false, goodId, goodId);
    }
}

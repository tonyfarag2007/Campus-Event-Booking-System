import java.util.ArrayList;

/*
 * The purpose of this class is sort of to provide a generic way to serialize and deserialize different structs.
 * */
public class CSVSerializable {
    /*
     * When you override this, it should return String and it should follow the same format as header().
     * For example if header is "id,name,email", serialize should have those fields as comma separated stuff.
     * */
    public String serialize() {
        // This is a placeholder, this "empty" would correspond to its respective "blank" column name (field).
        return "empty";
    }

    /*
     * As you add methods you must also update the header() method.
     *      * @return A fully instantiated object based on the fields.
     * */
    public String header() {
        return "blank";
    }

    /**
     * Each serializable class overrides this, it will have to call into the constructor of the class.
     * When you override this, replace CSVSerializable with your respective class name.
     * @param csv_values - An ArrayList containing each value, we already split it by commas.
     * @return A fully instantiated object based on the fields.
     */
    public CSVSerializable deserialize(ArrayList<String> csv_values) {
        return null;
    }

    public CSVSerializable(){};
}


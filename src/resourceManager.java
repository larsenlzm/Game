import java.io.*;

/**
 * Class for reading and writing files
 *
 * @author s325919, s325894
 */

class resourceManager {

    /**
     *
     * Writes an object to the stream
     *
     * @param data serialVersionUID
     * @param fileName name of the file.
     * @throws Exception
     */
    static void save(Serializable data, String fileName) throws Exception {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/saves/"+fileName))) {
            oos.writeObject(data);
        }
    }

    /**
     *
     * Makes the save file readable
     *
     * @param fileName
     * @return The object.
     * @throws Exception
     */
     static Object load(String fileName) throws Exception {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/saves/"+fileName))) {
            return ois.readObject();
        }
    }
}

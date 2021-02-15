package logic.task;

import logic.Engine;
import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import exception.data.TransPoolDataException;
import exception.data.TransPoolFileNotFoundException;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;


/**
 * A task that loads the data in the background.
 */
public class LoadFileTask extends Task<TransPoolData> {

    private File fileToLoad;
    private Engine engine;
    public LoadFileTask(Engine engine, File fileToLoad) {
        this.fileToLoad = fileToLoad;
        this.engine = engine;
    }

    /**
     * A function that loads the data.
     * @return
     */
    @Override
    protected TransPoolData call() {
        try {
            if (!fileToLoad.exists()) {
                throw new TransPoolFileNotFoundException();
            }

            Thread.sleep(1000);
            updateMessage("Fetching file...");
            JAXBContext jaxbContext = JAXBContext.newInstance(TransPool.class);
            Thread.sleep(1000);
            updateMessage("Loading file to system...");
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TransPool JAXBData = (TransPool) jaxbUnmarshaller.unmarshal(fileToLoad);
            Thread.sleep(2000);
            updateMessage("Parsing...");

            TransPoolData data = new TransPoolData(JAXBData);
            updateMessage("File loaded successfully!");
            engine.setData(data);
            return data;
        } catch (TransPoolDataException | TransPoolFileNotFoundException e) {
            updateMessage(e.getMessage());
            return null;
        } catch (JAXBException e) {
            updateMessage("There was an internal problem (JAXB Error).");
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

}

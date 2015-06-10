package cn.edu.nwsuaf.videoCapture.client.VideoRecorder;



import java.io.*;
import java.util.*;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.VideoFormat;


public class CamDataSource {
    
    private Component parent;
    
    private DataSource mainCamSource;
    private MediaLocator ml;
    private Processor processor;
    private boolean processing;
    
    public CamDataSource(Component parent) {
        this.parent = parent;
        setProcessing(false);
    }
    
    public void setMainSource(){
        setProcessing(false);
        VideoFormat vidformat = new VideoFormat(VideoFormat.RGB);
        Vector devices = CaptureDeviceManager.getDeviceList(vidformat);
        CaptureDeviceInfo di = null;
        
        if (devices.size() > 0) 
        di = (CaptureDeviceInfo) devices.elementAt(0);
        else {
            JOptionPane.showMessageDialog(parent, 
               "Your camera is not connected", "No webcam found", JOptionPane.WARNING_MESSAGE);
            return;
        }
       
        try {
            ml = di.getLocator();
            setMainCamSource(Manager.createDataSource(ml));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, 
               "Exception locating media: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
    
    public void makeDataSourceCloneable(){
        // turn our data source to a cloneable data source
        setMainCamSource(Manager.createCloneableDataSource(getMainCamSource()));
        
    }
    
    public void startProcessing(){
        
        try{
            processor = Manager.createProcessor(getMainCamSource());
        }catch (IOException e) {
//            JOptionPane.showMessageDialog(parent, 
//               "IO Exception creating processor: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }catch (NoProcessorException e) {
//            JOptionPane.showMessageDialog(parent, 
//               "Exception creating processor: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        CamStateHelper playhelper = new CamStateHelper(processor);
        if(!playhelper.configure(10000)){
            JOptionPane.showMessageDialog(parent, 
               "cannot configure processor", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        processor.setContentDescriptor(null);
        if(!playhelper.realize(10000)){
            JOptionPane.showMessageDialog(parent, 
               "cannot realize processor", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
       // In order for or your clones to start, you must start the original source
        processor.start();
        setProcessing(true);
    }
    
 
    public DataSource cloneCamSource(){
        if(!getProcessing()) setMainSource();
        return ((SourceCloneable)getMainCamSource()).createClone();
    }
    
    public DataSource getMainCamSource(){
        return mainCamSource;
    }

    public void setMainCamSource(DataSource mainCamSource){
        this.mainCamSource = mainCamSource;
    }
    
    public void setMl(MediaLocator ml){
        this.ml = ml;
    }
    
    public MediaLocator getMl(){
        return ml;
    }
    
    public boolean getProcessing(){
        return processing;
    }
    
    public void setProcessing(boolean processing){
        this.processing = processing;
        
    }
    
    public void setParent(Component parent){
        this.parent = parent;
    }
    
    public Component getParent(){
        return parent;
    }

}

/**********************************************************************************************/






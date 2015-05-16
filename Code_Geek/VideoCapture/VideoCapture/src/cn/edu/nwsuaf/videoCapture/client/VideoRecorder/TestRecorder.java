package cn.edu.nwsuaf.videoCapture.client.VideoRecorder;

import javax.swing.JFrame;

/******************************************************************************************/

public class TestRecorder {

public TestRecorder() {
    
    CamDataSource dataSource = new CamDataSource(null);
    dataSource.setMainSource();
    dataSource.makeDataSourceCloneable();
    dataSource.startProcessing();
    JFrame frame =new JFrame(); 
    VideoRecorder jpanel = new VideoRecorder(dataSource,frame);
    frame.add(jpanel);
    frame.setSize(400, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    }

public static void main(String[] args) {
    
TestRecorder Lvt = new TestRecorder();
    
}
}



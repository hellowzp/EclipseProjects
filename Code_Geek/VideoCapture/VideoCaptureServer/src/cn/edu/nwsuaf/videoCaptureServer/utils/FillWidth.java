
package cn.edu.nwsuaf.videoCaptureServer.utils;

import java.awt.Dimension;

import javax.swing.JPanel;

 /**
 * ����Ϊ�����BorderLayout�Ŀ��
 * @author	Qzhong
 */
public class FillWidth extends JPanel {

	public FillWidth(int width,int height) {
		setPreferredSize(new Dimension(width,height));
	}
}

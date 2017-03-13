/* -----------------------------------------------------------------------------
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
----------------------------------------------------------------------------- */

package ppm_java.util.debug;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

/**
 * The debug UI. For displaying runtime statistics. 
 * We expect plain vanilla textual information (set
 * via {@link #SetText(String)}).
 * 
 * @author Peter Hoppe
 */
public class TWndDebug extends JFrame
{
    /**
     * The debug window singleton.
     */
    private static TWndDebug            gWnd = new TWndDebug ();
    
    /**
     * Show the debug window.
     */
    public static void Show ()
    {
        gWnd.setVisible (true);
    }
    
    /**
     * Set the window's text content.
     * 
     * @param text      The test to set - which means, the statistics dump.
     */
    public static void SetText (String text)
    {
        gWnd.fTxtOutput.setText (text);
    }
    
    private static final long serialVersionUID = -3713894035663997050L;
    
    /**
     * The outer container.
     */
    private JPanel              contentPane;
    
    /**
     * Output here! 
     */
    private JTextArea           fTxtOutput;
    
    /**
     * Create the frame.
     */
    public TWndDebug ()
    {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Debug");
        setBounds (100, 100, 700, 732);
        contentPane = new JPanel ();
        contentPane.setBorder (new EmptyBorder (5, 5, 5, 5));
        contentPane.setLayout (new BorderLayout (0, 0));
        setContentPane (contentPane);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        fTxtOutput = new JTextArea();
        fTxtOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        fTxtOutput.setEditable(false);
        scrollPane.setViewportView(fTxtOutput);
    }
}

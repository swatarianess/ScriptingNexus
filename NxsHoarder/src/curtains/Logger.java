package curtains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Reall_blue on 02/01/2015.
 */
public class Logger extends OutputStream {
    private JTextArea log;
    private JDialog dialog;

    public Logger(){
        log = new JTextArea();
        log.setFont(new Font("Tahoma", Font.PLAIN, 12));

        dialog = new JDialog(Frame.getFrames()[0], "Script Logger", Dialog.ModalityType.MODELESS);

        dialog.setContentPane(new JPanel(new BorderLayout()) {{
            add(new JScrollPane(log) {{
                setPreferredSize(new Dimension(250, 100));
            }}, BorderLayout.CENTER);
            add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {{
                add(new JButton("Clear") {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            log.setText("");
                        }
                    });
                }});
            }}, BorderLayout.NORTH);
        }});
        dialog.pack();
    }


    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
    final String text = new String(buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(text);
            }
        });
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

    public void setVisible(boolean visible) {
        dialog.setLocationRelativeTo(dialog.getOwner());
        dialog.setVisible(visible);
    }

    public void dispose() {
    dialog.dispose();
    }
}

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Persistence {
    public static void main(String [] args) throws IOException {
        System.out.println("NEE ANATA WA ITSUMO YUME WO MITE MASU KA?");

        /*
           Месседж на русском, верхний угол, по закрытию ехекутит м.бат который скачивает месседж

        */
        Thread one = new Thread(() -> {
            try {
                Thread.sleep(1000);

                final JOptionPane pane = new JOptionPane("Вы всегда мечтаете?\n" +
                        "В этом мире, который почти закончился.\n" +
                        "Ты будешь тем, кто проложит заблокированный путь.\n" +
                        "Я нес послание. Это трудно.");
                final JDialog dialog = pane.createDialog(null, "Yume");
                dialog.setLocation(100, 100);
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);

                try {
                    Desktop.getDesktop().open(new File("download_and_raise_volume.bat"));
                    Desktop.getDesktop().open(new File("m.bat"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread two = new Thread(() -> {
            try {
                Thread.sleep(2002);

                final JOptionPane pane = new JOptionPane("Hey, are you always dreaming?\n" +
                        "In this world that's just about to end\n" +
                        "You're the one who's gonna pave the road that's been blocked.\n" +
                        "I've been carrying a message. It's hard.");
                final JDialog dialog = pane.createDialog(null, "Yumeeeee");
                dialog.setLocation(200, 300);
                dialog.setVisible(true);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread three = new Thread(() -> {
            try {
                Thread.sleep(2001);

                final JOptionPane pane = new JOptionPane("ねえ　貴方はいつも　夢を見てますか\n" +
                        "終わりかけのこの世界で\n" +
                        "塞がれた道を　拓く者になる\n" +
                        "「メッセージ」を抱いていた　つらいよ");
                final JDialog dialog = pane.createDialog(null, "Yumeeeee");
                dialog.setLocation(600, 250);
                dialog.setVisible(true);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        /*
        *
        * Месседж на хохляцком, сильно справа, по закрытию открывает месседж
        * */
        Thread four = new Thread(() -> {
            try {
                Thread.sleep(2000);

                final JOptionPane pane = new JOptionPane("Агов, ти завжди мрієш?\n" +
                        "У цьому світі, який майже закінчився\n" +
                        "Ти будеш тим, хто прокладеш заблокований шлях.\n" +
                        "Я везла повідомлення. Це важко.");
                final JDialog dialog = pane.createDialog(null, "Yumeeeee");
                dialog.setLocation(950, 250);
                dialog.setVisible(true);

                try {
                    Desktop.getDesktop().open(new File("the_dying_message.mp3"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        one.start();
        two.start();
        three.start();
        four.start();

        JOptionPane.showConfirmDialog(null, "NEE ANATA WA ITSUMO YUME WO MITE MASU KA?", "Yume", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

    }
}

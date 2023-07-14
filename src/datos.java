import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class datos {
    private JButton guardarDatosEnElButton;
    private JTextField apellido;
    private JTextField nombre;
    private JTextField cedula;
    private JTextField codigo;
    private JComboBox comboBox1;
    private JButton cargarDatosDesdeElButton;
    private JButton retrocede;
    private JButton avanza;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JCheckBox rojoCheckBox;
    private JCheckBox verdeCheckBox;
    private JCheckBox ningunoCheckBox;
    private JRadioButton siRadioButton;
    private JRadioButton noRadioButton;
    private JPanel marco;
    private String filePath= "data.dat";
    private int item=0;

    private ArrayList<Registro> registros = new ArrayList<Registro>();

    Registro miobjeto= new Registro(codigo.getText(), cedula.getText(), nombre.getText(), apellido.getText());

    public datos() {
        cargarDatosDesdeElButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try(
                        FileInputStream fileInt = new FileInputStream(filePath);
                        ObjectInputStream objInt = new ObjectInputStream(fileInt);
                ) {
                    Registro readObject = (Registro) objInt.readObject();
                    codigo.setText(readObject.getCodigo());
                    cedula.setText(readObject.getCedula());
                    nombre.setText(readObject.getNombre());
                    apellido.setText(readObject.getApellido());

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        guardarDatosEnElButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try(
                        FileOutputStream fileOut = new FileOutputStream(filePath);
                        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                ) {
                    objOut.writeObject(miobjeto);
                    System.out.println("Archivo escrito correctamente");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        avanza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Registro miobjeto= new Registro(codigo.getText(), cedula.getText(), nombre.getText(), apellido.getText());

                for( int i=0; i<=item; i=i+1 ){
                    registros.add(miobjeto);
                }

                try(
                        FileOutputStream fileOut = new FileOutputStream(filePath);
                        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                ) {

                    for(int i=0; i<registros.size(); i++){
                        if((registros.get(i++).equals(registros.get(i-1)))) break;
                        else{
                            objOut.writeObject(registros.get(i).getCodigo());
                            objOut.writeObject(registros.get(i).getCedula());
                            objOut.writeObject(registros.get(i).getNombre());
                            objOut.writeObject(registros.get(i).getApellido());

                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(new RuntimeException(ex));
                }

                codigo.setText("");
                cedula.setText("");
                nombre.setText("");
                apellido.setText("");

            }
        });
        retrocede.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try(
                        FileInputStream fileInt = new FileInputStream(filePath);
                        ObjectInputStream objInt = new ObjectInputStream(fileInt);
                ) {
                    for(Registro registro: registros){
                        codigo.setText(""+registro.getCodigo());
                        cedula.setText(""+registro.getCedula());
                        nombre.setText(registro.getNombre());
                        apellido.setText(registro.getApellido());
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("datos");
        frame.setContentPane(new datos().marco);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}

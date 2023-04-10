
/**
 * Ernie Yu - Github
 * https://github.com/ernieyu/Swing-range-slider
 * 
 */
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RangeSliderWindow extends JPanel implements ActionListener, ChangeListener, ItemListener {
	private static final long serialVersionUID = 1L;

	private FinalProject finalProject;
	// MIN-MAX RANGE SLIDER
	private RangeSlider radius_slider = new RangeSlider();
	private RangeSlider length_slider = new RangeSlider();
	private RangeSlider angle_range_slider = new RangeSlider();
	// TICK MARK SLIDER
	private JSlider pixel_gap_slider; // single-input slider to control pixel interval
	private JSlider angle_slider; // single-input slider to control pixel interval
	// CHECKBOX
	private Checkbox angle_option = new Checkbox("Set angle", true);
	private Checkbox angle_range_option = new Checkbox("Angle Range", false);
	private Checkbox orientation_angle_option = new Checkbox("Random Angle", false);

	// TITLE AND LABEL
	private JLabel radius_slider_title = new JLabel();
	private JLabel radius_slider_label_min = new JLabel();
	private JLabel radius_slider_value_min = new JLabel();
	private JLabel radius_slider_label_max = new JLabel();
	private JLabel range_slider_value_max = new JLabel();

	private JLabel length_slider_title = new JLabel();
	private JLabel length_slider_label_min = new JLabel();
	private JLabel length_slider_value_min = new JLabel();
	private JLabel length_slider_label_max = new JLabel();
	private JLabel length_slider_value_max = new JLabel();

	private JLabel angle_range_slider_title = new JLabel();
	private JLabel angle_range_slider_label_min = new JLabel();
	private JLabel angle_range_slider_value_min = new JLabel();
	private JLabel angle_range_slider_label_max = new JLabel();
	private JLabel angle_range_slider_value_max = new JLabel();

	private JLabel pixel_gap_slider_title = new JLabel();
	private JLabel angle_slider_title = new JLabel();

	private JButton generate = new JButton(); // apply the current parameters

	/**
	 * Create the UI control window.
	 * 
	 * @param finalProject the finalProject instance
	 */
	public RangeSliderWindow(FinalProject finalProject) {
		Border empty = BorderFactory.createRaisedBevelBorder();

		setBorder(empty);
		setLayout(new GridBagLayout());
		this.finalProject = finalProject;

		// JSlider(orientation,mixValue, maxValue, value);

		pixel_gap_slider = new JSlider(JSlider.HORIZONTAL, finalProject.min_slider_pixel, finalProject.max_slider_pixel,
				finalProject.pixel_gap);
		angle_slider = new JSlider(JSlider.HORIZONTAL, finalProject.min_slider_theta, finalProject.max_slider_theta,
				finalProject.theta);

		//
		pixel_gap_slider.addChangeListener(this);
		angle_slider.addChangeListener(this);

		length_slider.addChangeListener(this);
		radius_slider.addChangeListener(this);
		angle_option.addItemListener(this);
		angle_range_option.addItemListener(this);
		angle_range_slider.addChangeListener(this);
		orientation_angle_option.addItemListener(this);

		// pixel gap
		pixel_gap_slider_title.setText("Pixel Gap/Increment");
		pixel_gap_slider.setPreferredSize(new Dimension(240, 40));
		pixel_gap_slider.setMajorTickSpacing(2);
		pixel_gap_slider.setPaintTicks(true);
		pixel_gap_slider.setPaintLabels(true);
		pixel_gap_slider.setSnapToTicks(true);

		angle_slider_title.setText("Choose angle");
		angle_slider.setPreferredSize(new Dimension(240, 40));
		angle_slider.setMajorTickSpacing(45);
		angle_slider.setPaintTicks(true);
		angle_slider.setPaintLabels(true);
		angle_slider.setSnapToTicks(true);

		// radius slider

		radius_slider.setPreferredSize(new Dimension(240, radius_slider.getPreferredSize().height));
		radius_slider.setMinimum(finalProject.min_slider_radius);
		radius_slider.setMaximum(finalProject.max_slider_radius);
		radius_slider.setValue(finalProject.min_slider_radius);
		radius_slider.setUpperValue(finalProject.max_slider_radius);
		radius_slider_title.setText("STROKE RADIUS:");
		radius_slider_label_min.setText("Min value:");
		radius_slider_label_max.setText("Max value:");

		// length slider
		length_slider.setPreferredSize(new Dimension(240, length_slider.getPreferredSize().height));
		length_slider.setMinimum(finalProject.min_slider_length);
		length_slider.setMaximum(finalProject.max_slider_length);
		length_slider.setValue(finalProject.min_slider_length);
		length_slider.setUpperValue(finalProject.max_slider_length);
		length_slider_title.setText("STROKE LENGTH:");
		length_slider_label_min.setText("Min value:");
		length_slider_label_max.setText("Max value:");

		// angle range slider
		angle_range_slider.setPreferredSize(new Dimension(240, angle_range_slider.getPreferredSize().height));
		angle_range_slider.setMinimum(finalProject.min_slider_theta);
		angle_range_slider.setMaximum(finalProject.max_slider_theta);
		angle_range_slider.setValue(finalProject.min_slider_theta);
		angle_range_slider.setUpperValue(finalProject.max_slider_theta);
		angle_range_slider_title.setText("ANGLE RANGE:");
		angle_range_slider_label_min.setText("Min value:");
		angle_range_slider_label_max.setText("Max value:");

		generate.setText("Generate");

		// Add action listeners to buttons
		generate.addActionListener(this);

		add(pixel_gap_slider_title, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(pixel_gap_slider, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(angle_option, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(angle_range_option, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(orientation_angle_option, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		// angle set slider
		add(angle_slider_title, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(angle_slider, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		// angle range set slider
		add(angle_range_slider_title, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(angle_range_slider, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(angle_range_slider_label_min, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(angle_range_slider_value_min, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(angle_range_slider_label_max, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(angle_range_slider_value_max, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider, new GridBagConstraints(0, 10, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider_title, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider_label_min, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(radius_slider_value_min, new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider_label_max, new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(range_slider_value_max, new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider, new GridBagConstraints(0, 14, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider_title, new GridBagConstraints(0, 15, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider_label_min, new GridBagConstraints(0, 16, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(length_slider_value_min, new GridBagConstraints(1, 16, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider_label_max, new GridBagConstraints(0, 17, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(length_slider_value_max, new GridBagConstraints(1, 17, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider, new GridBagConstraints(0, 18, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(generate, new GridBagConstraints(0, 19, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
	}

	/**
	 * Called when a slider is changed.
	 * 
	 * @param e the event associated with the change
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == length_slider) {
			length_slider_value_min.setText(String.valueOf(length_slider.getValue()));
			length_slider_value_max.setText(String.valueOf(length_slider.getUpperValue()));
		} else if (e.getSource() == radius_slider) {
			radius_slider_value_min.setText(String.valueOf(radius_slider.getValue()));
			range_slider_value_max.setText(String.valueOf(radius_slider.getUpperValue()));
		} else if (e.getSource() == angle_range_slider) {
			angle_range_slider_value_min.setText(String.valueOf(angle_range_slider.getValue()));
			angle_range_slider_value_max.setText(String.valueOf(angle_range_slider.getUpperValue()));
		}
	}

	/**
	 * Called when a button is clicked.
	 * 
	 * @param event the event associated with the click
	 */
	public void actionPerformed(ActionEvent event) {
		generate();
	}

	private void generate() {
		finalProject.pixel_gap = pixel_gap_slider.getValue();
		finalProject.theta = (angle_slider.getValue());
		finalProject.min_max(Parameter.RADIUS, radius_slider.getValue(), radius_slider.getUpperValue());
		finalProject.min_max(Parameter.LENGTH, length_slider.getValue(), length_slider.getUpperValue());
		finalProject.min_max(Parameter.THETA_RANGE, angle_range_slider.getValue(), angle_range_slider.getUpperValue());

		finalProject.SET_THETA = angle_option.getState();
		finalProject.ANGLE_RANGE = angle_range_option.getState();
		finalProject.ORIENTATION = orientation_angle_option.getState();

	}

	public void sliderValue() {
		radius_slider_value_min.setText("" + radius_slider.getValue());
		range_slider_value_max.setText("" + radius_slider.getUpperValue());
		length_slider_value_min.setText("" + length_slider.getValue());
		length_slider_value_max.setText("" + length_slider.getUpperValue());
		angle_range_slider_value_min.setText("" + angle_range_slider.getValue());
		angle_range_slider_value_max.setText("" + angle_range_slider.getUpperValue());
	}

	public void setFrame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("UI Controls");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void display() {
		sliderValue();
		setFrame();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

}

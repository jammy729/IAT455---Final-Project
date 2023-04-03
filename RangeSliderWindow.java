import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RangeSliderWindow extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private FinalProject finalProject; // reference to the finalProject
	private RangeSlider radius_slider = new RangeSlider(); // double-input slider to control stroke radius
	private RangeSlider length_slider = new RangeSlider(); // double-input slider to control stroke length

	private JSlider pixel_gap_slider; // single-input slider to control pixel interval

	// labels for sliders and values
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

	private JLabel pixel_gap_sliderTitle = new JLabel();
	private JButton applyBtn = new JButton("Apply"); // apply the current parameters

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
		pixel_gap_slider = new JSlider(JSlider.HORIZONTAL, finalProject.min_slider_interval,
				finalProject.max_slider_interval, finalProject.pixel_gap);

		//
		pixel_gap_slider.addChangeListener(this);
		length_slider.addChangeListener(this);
		radius_slider.addChangeListener(this);

		// pixel gap
		/*
		 * keep this??
		 */
		pixel_gap_sliderTitle.setText("Pixel Gap/Increment");
		pixel_gap_slider.setPreferredSize(new Dimension(240, 40));
		pixel_gap_slider.setMajorTickSpacing(1);
		pixel_gap_slider.setPaintTicks(true);
		pixel_gap_slider.setPaintLabels(true);
		pixel_gap_slider.setSnapToTicks(true);

		// radius slider

		radius_slider.setPreferredSize(new Dimension(240, radius_slider.getPreferredSize().height));
		radius_slider.setMinimum(finalProject.min_slider_radius);
		radius_slider.setMaximum(finalProject.max_slider_radius);
		radius_slider.setValue(finalProject.min_slider_radius);
		radius_slider.setUpperValue(finalProject.max_slider_radius);
		radius_slider_title.setText("Stroke radius");
		radius_slider_label_min.setText("Min value:");
		radius_slider_label_max.setText("Max value:");

		// length slider
		length_slider.setPreferredSize(new Dimension(240, length_slider.getPreferredSize().height));
		length_slider.setMinimum(finalProject.min_slider_length);
		length_slider.setMaximum(finalProject.max_slider_length);
		length_slider.setValue(finalProject.min_slider_length);
		length_slider.setUpperValue(finalProject.max_slider_length);
		length_slider_title.setText("Stroke length");
		length_slider_label_min.setText("Min value:");
		length_slider_label_max.setText("Max value:");

		// Add action listeners to buttons
		applyBtn.addActionListener(this);

		add(pixel_gap_sliderTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(pixel_gap_slider, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider_title, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider_label_min, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(radius_slider_value_min, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider_label_max, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(range_slider_value_max, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(radius_slider, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider_title, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider_label_min, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(length_slider_value_min, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider_label_max, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
		add(length_slider_value_max, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		add(length_slider, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

		/*
		 * buttons
		 */
		add(applyBtn, new GridBagConstraints(0, 16, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
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
		}
	}

	/**
	 * Called when a button is clicked.
	 * 
	 * @param event the event associated with the click
	 */
	public void actionPerformed(ActionEvent event) {
		cta();
	}

	private void cta() {
		finalProject.pixel_gap = pixel_gap_slider.getValue();
		finalProject.min_max(Parameter.RADIUS, radius_slider.getValue(), radius_slider.getUpperValue());
		finalProject.min_max(Parameter.LENGTH, length_slider.getValue(), length_slider.getUpperValue());
	}

	public void sliderValue() {
		radius_slider_value_min.setText("" + radius_slider.getValue());
		range_slider_value_max.setText("" + radius_slider.getUpperValue());
		length_slider_value_min.setText("" + length_slider.getValue());
		length_slider_value_max.setText("" + length_slider.getUpperValue());
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

}

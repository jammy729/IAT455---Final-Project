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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RangeSliderWindow extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private FinalProject finalProject; // reference to the finalProject
	private RangeSlider radiusSlider = new RangeSlider(); // double-input slider to control stroke radius
	private RangeSlider lengthSlider = new RangeSlider(); // double-input slider to control stroke length

	private JSlider pixelIntervalSlider; // single-input slider to control pixel interval

	// labels for sliders and values
	private JLabel radiusSliderTitle = new JLabel();
	private JLabel radiusSliderLabel1 = new JLabel();
	private JLabel radiusSliderValue1 = new JLabel();
	private JLabel radiusSliderLabel2 = new JLabel();
	private JLabel radiusSliderValue2 = new JLabel();

	private JLabel lengthSliderTitle = new JLabel();
	private JLabel lengthSliderLabel1 = new JLabel();
	private JLabel lengthSliderValue1 = new JLabel();
	private JLabel lengthSliderLabel2 = new JLabel();
	private JLabel lengthSliderValue2 = new JLabel();

	private JLabel pixelIntervalSliderTitle = new JLabel();
	private JButton applyBtn = new JButton("Apply"); // apply the current parameters

	/**
	 * Create the UI control window.
	 * 
	 * @param finalProject the finalProject instance
	 */
	public RangeSliderWindow(FinalProject finalProject) {
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		setLayout(new GridBagLayout());
		this.finalProject = finalProject;

//		JSlider(orientation,mixValue, maxValue, value);
		pixelIntervalSlider = new JSlider(JSlider.HORIZONTAL, finalProject.minAllowedPixelInterval,
				finalProject.maxAllowedPixelInterval, finalProject.pixelInterval);

		// add change listeners to sliders
		pixelIntervalSlider.addChangeListener(this);
		lengthSlider.addChangeListener(this);
		radiusSlider.addChangeListener(this);

		// create pixel interval slider
		pixelIntervalSliderTitle.setText("Pixel interval");
		pixelIntervalSlider.setPreferredSize(new Dimension(240, 40));
		pixelIntervalSlider.setMajorTickSpacing(1);
		pixelIntervalSlider.setPaintTicks(true);
		pixelIntervalSlider.setPaintLabels(true);
		pixelIntervalSlider.setSnapToTicks(true);

		// create radius slider

		radiusSlider.setPreferredSize(new Dimension(240, radiusSlider.getPreferredSize().height));
		radiusSlider.setMinimum(finalProject.minAllowedStrokeRadius);
		radiusSlider.setMaximum(finalProject.maxAllowedStrokeRadius);
		radiusSlider.setValue(finalProject.minStrokeRadius);
		radiusSlider.setUpperValue(finalProject.maxStrokeRadius);
		radiusSliderTitle.setText("Stroke radius");
		radiusSliderLabel1.setText("Lower value:");
		radiusSliderLabel2.setText("Upper value:");

		// create length slider

		lengthSlider.setPreferredSize(new Dimension(240, lengthSlider.getPreferredSize().height));
		lengthSlider.setMinimum(finalProject.minAllowedStrokeLength);
		lengthSlider.setMaximum(finalProject.maxAllowedStrokeLength);
		lengthSlider.setValue(finalProject.minStrokeLength);
		lengthSlider.setUpperValue(finalProject.maxStrokeLength);
		lengthSliderTitle.setText("Stroke length");
		lengthSliderLabel1.setText("Lower value:");
		lengthSliderLabel2.setText("Upper value:");

		// Add action listeners to buttons
		applyBtn.addActionListener(this);

		Insets noInsets = new Insets(0, 0, 0, 0);
		Insets marginTop = new Insets(16, 0, 4, 0);

		/*
		 * PIXEL INTERVAL SLIDER
		 */

		// Row 0
		add(pixelIntervalSliderTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 1
		add(pixelIntervalSlider, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		/*
		 * RADIUS SLIDER
		 */

		// Row 2
		add(radiusSliderTitle, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 3
		add(radiusSliderLabel1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(radiusSliderValue1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 4
		add(radiusSliderLabel2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(radiusSliderValue2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 5
		add(radiusSlider, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		/*
		 * LENGTH SLIDER
		 */

		// Row 6
		add(lengthSliderTitle, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, marginTop, 0, 0));

		// Row 7
		add(lengthSliderLabel1, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(lengthSliderValue1, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 8
		add(lengthSliderLabel2, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
		add(lengthSliderValue2, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		// Row 9
		add(lengthSlider, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));

		/*
		 * buttons
		 */
		add(applyBtn, new GridBagConstraints(0, 16, 2, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, noInsets, 0, 0));
	}

	/**
	 * Called when a slider is changed.
	 * 
	 * @param e the event associated with the change
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == lengthSlider) {
			lengthSliderValue1.setText(String.valueOf(lengthSlider.getValue()));
			lengthSliderValue2.setText(String.valueOf(lengthSlider.getUpperValue()));
			System.out.println("LENGTH SLIDER CHANGED");
		} else if (e.getSource() == radiusSlider) {
			radiusSliderValue1.setText(String.valueOf(radiusSlider.getValue()));
			radiusSliderValue2.setText(String.valueOf(radiusSlider.getUpperValue()));
			System.out.println("RADIUS SLIDER CHANGED");
		} else if (e.getSource() == pixelIntervalSlider) {
			if (!pixelIntervalSlider.getValueIsAdjusting()) {
				System.out.println("pixel interval slider changed");
			}
		}
	}

	/**
	 * Called when a button is clicked.
	 * 
	 * @param event the event associated with the click
	 */
	public void actionPerformed(ActionEvent event) {
		JButton clickedButton = (JButton) event.getSource();
		if (clickedButton == applyBtn) {
			apply();
		}
	}

	/** Apply the slider values to the finalProject class. */
	private void apply() {
		finalProject.pixelInterval = pixelIntervalSlider.getValue();
		finalProject.setRange(Parameter.radius, radiusSlider.getValue(), radiusSlider.getUpperValue());
		finalProject.setRange(Parameter.length, lengthSlider.getValue(), lengthSlider.getUpperValue());
	}

	/** Display the UI control window. */
	public void display() {
		// Display the current values of the sliders
		radiusSliderValue1.setText(String.valueOf(radiusSlider.getValue()));
		radiusSliderValue2.setText(String.valueOf(radiusSlider.getUpperValue()));
		lengthSliderValue1.setText(String.valueOf(lengthSlider.getValue()));
		lengthSliderValue2.setText(String.valueOf(lengthSlider.getUpperValue()));

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
}

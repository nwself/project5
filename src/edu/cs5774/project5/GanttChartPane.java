package edu.cs5774.project5;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class GanttChartPane extends JPanel {

	public GanttChartPane(Project project) {
		super(new GridLayout(0, 1));
		
		//this.add(new JLabel("Gantt Chart for " + project.getName()));
		
		IntervalCategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		this.add(chartPanel);
	}

	private JFreeChart createChart(final IntervalCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            "Gantt Chart Demo",  // chart title
            "Task",              // domain axis label
            "Date",              // range axis label
            dataset,             // data
            true,                // include legend
            true,                // tooltips
            false                // urls
        );    
//        chart.getCategoryPlot().getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
        return chart;    
    }

	private IntervalCategoryDataset createDataset(Project project) {
		return null;
	}


	public static IntervalCategoryDataset createDataset() {

        final TaskSeries s1 = new TaskSeries("Scheduled");
        s1.add(new Task("Write Proposal",
               new SimpleTimePeriod(date(1, Calendar.APRIL, 2001),
                                    date(5, Calendar.APRIL, 2001))));
        s1.add(new Task("Obtain Approval",
               new SimpleTimePeriod(date(9, Calendar.APRIL, 2001),
                                    date(9, Calendar.APRIL, 2001))));
        s1.add(new Task("Requirements Analysis",
               new SimpleTimePeriod(date(10, Calendar.APRIL, 2001),
                                    date(5, Calendar.MAY, 2001))));
        s1.add(new Task("Design Phase",
               new SimpleTimePeriod(date(6, Calendar.MAY, 2001),
                                    date(30, Calendar.MAY, 2001))));
        s1.add(new Task("Design Signoff",
               new SimpleTimePeriod(date(2, Calendar.JUNE, 2001),
                                    date(2, Calendar.JUNE, 2001))));
        s1.add(new Task("Alpha Implementation",
               new SimpleTimePeriod(date(3, Calendar.JUNE, 2001),
                                    date(31, Calendar.JULY, 2001))));
        s1.add(new Task("Design Review",
               new SimpleTimePeriod(date(1, Calendar.AUGUST, 2001),
                                    date(8, Calendar.AUGUST, 2001))));
        s1.add(new Task("Revised Design Signoff",
               new SimpleTimePeriod(date(10, Calendar.AUGUST, 2001),
                                    date(10, Calendar.AUGUST, 2001))));
        s1.add(new Task("Beta Implementation",
               new SimpleTimePeriod(date(12, Calendar.AUGUST, 2001),
                                    date(12, Calendar.SEPTEMBER, 2001))));
        s1.add(new Task("Testing",
               new SimpleTimePeriod(date(13, Calendar.SEPTEMBER, 2001),
                                    date(31, Calendar.OCTOBER, 2001))));
        s1.add(new Task("Final Implementation",
               new SimpleTimePeriod(date(1, Calendar.NOVEMBER, 2001),
                                    date(15, Calendar.NOVEMBER, 2001))));
        s1.add(new Task("Signoff",
               new SimpleTimePeriod(date(28, Calendar.NOVEMBER, 2001),
                                    date(30, Calendar.NOVEMBER, 2001))));

        final TaskSeries s2 = new TaskSeries("Actual");
        s2.add(new Task("Write Proposal",
               new SimpleTimePeriod(date(1, Calendar.APRIL, 2001),
                                    date(5, Calendar.APRIL, 2001))));
        s2.add(new Task("Obtain Approval",
               new SimpleTimePeriod(date(9, Calendar.APRIL, 2001),
                                    date(9, Calendar.APRIL, 2001))));
        s2.add(new Task("Requirements Analysis",
               new SimpleTimePeriod(date(10, Calendar.APRIL, 2001),
                                    date(15, Calendar.MAY, 2001))));
        s2.add(new Task("Design Phase",
               new SimpleTimePeriod(date(15, Calendar.MAY, 2001),
                                    date(17, Calendar.JUNE, 2001))));
        s2.add(new Task("Design Signoff",
               new SimpleTimePeriod(date(30, Calendar.JUNE, 2001),
                                    date(30, Calendar.JUNE, 2001))));
        s2.add(new Task("Alpha Implementation",
               new SimpleTimePeriod(date(1, Calendar.JULY, 2001),
                                    date(12, Calendar.SEPTEMBER, 2001))));
        s2.add(new Task("Design Review",
               new SimpleTimePeriod(date(12, Calendar.SEPTEMBER, 2001),
                                    date(22, Calendar.SEPTEMBER, 2001))));
        s2.add(new Task("Revised Design Signoff",
               new SimpleTimePeriod(date(25, Calendar.SEPTEMBER, 2001),
                                    date(27, Calendar.SEPTEMBER, 2001))));
        s2.add(new Task("Beta Implementation",
               new SimpleTimePeriod(date(27, Calendar.SEPTEMBER, 2001),
                                    date(30, Calendar.OCTOBER, 2001))));
        s2.add(new Task("Testing",
               new SimpleTimePeriod(date(31, Calendar.OCTOBER, 2001),
                                    date(17, Calendar.NOVEMBER, 2001))));
        s2.add(new Task("Final Implementation",
               new SimpleTimePeriod(date(18, Calendar.NOVEMBER, 2001),
                                    date(5, Calendar.DECEMBER, 2001))));
        s2.add(new Task("Signoff",
               new SimpleTimePeriod(date(10, Calendar.DECEMBER, 2001),
                                    date(11, Calendar.DECEMBER, 2001))));

        final TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);
        collection.add(s2);

        return collection;
    }

	private static Date date(final int day, final int month, final int year) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final Date result = calendar.getTime();
        return result;

    }

}

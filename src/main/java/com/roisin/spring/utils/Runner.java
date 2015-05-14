package com.roisin.spring.utils;

import static com.roisin.spring.utils.Constants.FORMAT_ARFF;
import static com.roisin.spring.utils.Constants.FORMAT_CSV;
import static com.roisin.spring.utils.Constants.FORMAT_XLS;
import static com.roisin.spring.utils.Constants.FORMAT_XLSX;
import static com.roisin.spring.utils.Constants.FORMAT_XRFF;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.operator.learner.subgroups.RuleSet;
import com.roisin.core.processes.GenericProcesses;
import com.roisin.core.processes.Preprocessing;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.SubgroupResults;
import com.roisin.spring.model.RipperSettings;
import com.roisin.spring.model.SubgroupSettings;
import com.roisin.spring.model.TreeToRulesSettings;

import exception.RoisinException;

public class Runner {

	/**
	 * Class log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

	/**
	 * Rapidminer instance started variable
	 */
	public static boolean started;

	public static ExampleSet getExampleSetFromFile(final String fileFormat, final String path) {
		checkStarted();

		final Process process = Preprocessing.getExampleSetFromFileProcess(fileFormat, path);
		IOContainer container;
		ExampleSet res = null;
		try {
			container = process.run();
			res = (ExampleSet) container.asList().get(0);
		} catch (OperatorException e) {
			LOG.error("No ha sido posible extraer información del fichero");
		}
		return res;
	}

	public static ExampleSet getPreprocessedExampleSetFromFile(final String inputPath,
			final SortedSet<Integer> rowFilter, final String filterCondition,
			final List<String> attributeSelection) {
		checkStarted();

		ExampleSet res = null;
		Process process;
		try {
			process = Preprocessing.getPreprocessedExampleSet(inputPath, rowFilter,
					filterCondition, attributeSelection);
			IOContainer container;
			container = process.run();
			res = (ExampleSet) container.asList().get(0);
		} catch (OperatorException e) {
			LOG.error("No ha sido posible preprocesar la información del fichero", e);
		} catch (RoisinException e1) {
			LOG.error("No ha sido posible preprocesar la información del fichero", e1);
		}
		return res;
	}

	public static RoisinResults getRipperResults(final RipperSettings form, final String filePath,
			final String label, final String filterCondition,
			final List<String> attributeSelection, final SortedSet<Integer> deletedRows,
			final boolean discretizeLabel) {
		checkStarted();

		RipperResults results = null;
		try {
			Process process = GenericProcesses.getRipper(filePath, label, deletedRows,
					filterCondition, attributeSelection, form.getRipperCriterion(), form
							.getSampleRatio().toString(), form.getPureness().toString(), form
							.getMinimalPruneBenefit().toString(), discretizeLabel);
			final IOContainer container = process.run();
			final RuleModel ruleModel = (RuleModel) container.asList().get(0);
			final ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			LOG.error("No ha sido posible ejecutar el proceso con Ripper");
		}
		return results;
	}

	public static RoisinResults getSubgroupResults(final SubgroupSettings form,
			final String filePath, final String label, final String filterCondition,
			final List<String> attributeSelection, final SortedSet<Integer> deletedRows) {
		checkStarted();

		SubgroupResults results = null;
		try {
			Process process = GenericProcesses.getSubgroupDiscoveryDiscretization(filePath, label,
					deletedRows, filterCondition, attributeSelection, form.getMode(), form
							.getUtilityFunction(), form.getMinUtility().toString(), form
							.getKBestRules().toString(), form.getRuleGeneration().toString(), form
							.getMaxDepth().toString(), form.getMinCoverage().toString());
			final IOContainer container = process.run();
			final RuleSet ruleModel = (RuleSet) container.asList().get(0);
			final ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new SubgroupResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			LOG.error("No ha sido posible ejecutar el proceso con Subgroup Discovery");
		}
		return results;
	}

	public static RoisinResults getTreeToRulesResults(final TreeToRulesSettings form,
			final String filePath, final String label, final String filterCondition,
			final List<String> attributeSelection, final SortedSet<Integer> deletedRows,
			final boolean discretizeLabel) {
		checkStarted();

		RipperResults results = null;
		try {
			Process process = GenericProcesses.getDecisionTreeToRules(filePath, label, deletedRows,
					filterCondition, attributeSelection, form.getTree2RulesCriterion(), form
							.getMinimalSizeForSplit().toString(), form.getMinimalLeafSize()
							.toString(), form.getMinimalGain().toString(), form.getMaximalDepth()
							.toString(), form.getConfidence().toString(), form
							.getNumberOfPrepruningAlternatives().toString(), form.getNoPrepruning()
							.toString(), form.getNoPruning().toString(), discretizeLabel);
			final IOContainer container = process.run();
			final RuleModel ruleModel = (RuleModel) container.asList().get(0);
			final ExampleSet exampleSet = (ExampleSet) container.asList().get(1);
			results = new RipperResults(ruleModel, exampleSet);
		} catch (OperatorException e) {
			LOG.error("No ha sido posible ejecutar el proceso con Decision Tree to rules");
		}
		return results;
	}

	/**
	 * This method executes the process that does the preprocessing step and
	 * stores all the preprocessed information in the server. Finally, it
	 * returns a ByteArrayOutputStream with the file stored in the server.
	 * 
	 * @param inputFormat
	 * @param inputPath
	 * @param rowFilt2er
	 * @param filterCondition
	 * @param attributeSelection
	 * @param outputFormat
	 * @param outputPath
	 * @return
	 */
	public static ByteArrayOutputStream exportData(final String inputPath,
			final SortedSet<Integer> rowFilter, final String filterCondition,
			final List<String> attributeSelection, final String outputPath) {

		checkStarted();

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Process process = Preprocessing.getPreprocessedDataFile(inputPath, rowFilter,
					filterCondition, attributeSelection, outputPath);
			process.run();
			final FileInputStream fis = new FileInputStream(new File(outputPath));
			final byte[] buf = new byte[1024];
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				// Writes len bytes from the specified byte array starting at
				// offset off to this byte array output stream.
				System.out.println("read " + readNum + " bytes,");
			}
			fis.close();
		} catch (Exception e) {
			LOG.error("No ha sido posible exportar el fichero", e);
		}
		return bos;
	}

	public static ExampleSet getRequestedExamples(final String inputPath,
			final String filterCondition) {

		checkStarted();

		final Process process = Preprocessing.getConditionFilteredData(inputPath, filterCondition);
		IOContainer container;
		ExampleSet res = null;
		try {
			container = process.run();
			res = (ExampleSet) container.asList().get(0);
		} catch (OperatorException e) {
			LOG.error("No ha sido posible extraer información del fichero");
		}
		return res;
	}

	public static void convertFile(final String inputFormat, final String outputFormat,
			final String inputPath, final String outputPath) {

		checkStarted();

		Process process = null;

		if ((inputFormat.equalsIgnoreCase(FORMAT_XLSX) || inputFormat.equalsIgnoreCase(FORMAT_XLSX))
				&& outputFormat.equals(FORMAT_CSV)) {
			process = com.roisin.core.processes.DataTransformation.convertExcelToCsv(inputPath,
					outputPath);
		}

		if ((inputFormat.equalsIgnoreCase(FORMAT_XLSX) || inputFormat.equalsIgnoreCase(FORMAT_XLSX))
				&& outputFormat.equals(FORMAT_ARFF)) {
			process = com.roisin.core.processes.DataTransformation.convertExcelToArff(inputPath,
					outputPath);
		}

		if ((inputFormat.equalsIgnoreCase(FORMAT_XLSX) || inputFormat.equalsIgnoreCase(FORMAT_XLSX))
				&& outputFormat.equals(FORMAT_XRFF)) {
			process = com.roisin.core.processes.DataTransformation.convertExcelToXrff(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_XRFF) && outputFormat.equalsIgnoreCase(FORMAT_XLSX)) {
			process = com.roisin.core.processes.DataTransformation.convertXrffToXlsx(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_XRFF) && outputFormat.equalsIgnoreCase(FORMAT_XLS)) {
			process = com.roisin.core.processes.DataTransformation.convertXrffToXls(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_XRFF) && outputFormat.equalsIgnoreCase(FORMAT_CSV)) {
			process = com.roisin.core.processes.DataTransformation.convertXrffToCsv(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_XRFF) && outputFormat.equalsIgnoreCase(FORMAT_ARFF)) {
			process = com.roisin.core.processes.DataTransformation.convertXrffToArff(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_ARFF) && outputFormat.equalsIgnoreCase(FORMAT_XLSX)) {
			process = com.roisin.core.processes.DataTransformation.convertArffToXlsx(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_ARFF) && outputFormat.equalsIgnoreCase(FORMAT_XLS)) {
			process = com.roisin.core.processes.DataTransformation.convertArffToXls(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_ARFF) && outputFormat.equalsIgnoreCase(FORMAT_CSV)) {
			process = com.roisin.core.processes.DataTransformation.convertArffToCsv(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_ARFF) && outputFormat.equalsIgnoreCase(FORMAT_XRFF)) {
			process = com.roisin.core.processes.DataTransformation.convertArffToXrff(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_CSV) && outputFormat.equalsIgnoreCase(FORMAT_XLSX)) {
			process = com.roisin.core.processes.DataTransformation.convertCsvToXlsx(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_CSV) && outputFormat.equalsIgnoreCase(FORMAT_XLS)) {
			process = com.roisin.core.processes.DataTransformation.convertCsvToXls(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_CSV) && outputFormat.equalsIgnoreCase(FORMAT_XRFF)) {
			process = com.roisin.core.processes.DataTransformation.convertCsvToXrff(inputPath,
					outputPath);
		}

		if (inputFormat.equals(FORMAT_CSV) && outputFormat.equalsIgnoreCase(FORMAT_ARFF)) {
			process = com.roisin.core.processes.DataTransformation.convertCsvToArff(inputPath,
					outputPath);
		}

		try {
			process.run();
		} catch (OperatorException e) {
			LOG.error("No ha sido posible convertir el fichero", e);
		}

	}

	public static void startRapidminer() {
		LOG.info("Iniciando rapidminer");
		RapidMiner.setExecutionMode(ExecutionMode.APPSERVER);
		RapidMiner.init();
		started = true;
	}

	public static boolean isStarted() {
		return started;
	}

	public static void checkStarted() {
		if (!isStarted()) {
			startRapidminer();
		}
	}

}

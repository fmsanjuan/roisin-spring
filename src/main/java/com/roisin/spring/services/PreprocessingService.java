package com.roisin.spring.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.roisin.core.processes.Preprocessing;
import com.roisin.spring.model.PreprocessingForm;
import com.roisin.spring.utils.Constants;

import exception.RoisinException;

@Service
public class PreprocessingService {

	private static final Logger logger = LoggerFactory.getLogger(PreprocessingService.class);

	public ExampleSet getExampleSetFromFile(MultipartFile file, String filePath) {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		ExampleSet exampleSet = null;

		try {
			inputStream = file.getInputStream();
			File newFile = new File(filePath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			logger.info("Iniciando rapidminer");
			RapidMiner.setExecutionMode(ExecutionMode.APPSERVER);
			RapidMiner.init();
			logger.error("No ha sido posible iniciar Rapidminer. Revise la configuración.");
			Process process = Preprocessing
					.getExampleSetFromFileProcess(StringUtils.substringAfterLast(
							file.getOriginalFilename(), Constants.DOT_SYMBOL), filePath);
			IOContainer container = process.run();
			exampleSet = (ExampleSet) container.asList().get(0);
			outputStream.close();
		} catch (IOException e) {
			logger.error("Imposible subir el fichero al servidor", e);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exampleSet;
	}

	public PreprocessingForm calculateFilterCondition(PreprocessingForm form) {
		// Transformación de condición provisional
		if (!StringUtils.isBlank(form.getFilterValue())) {
			StringBuilder condition = new StringBuilder();
			condition.append(form.getFilterAttribute());
			if (form.getFilterOperator().equals(Constants.EQUALS)) {
				condition.append(Constants.EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.NON_EQUALS)) {
				condition.append(Constants.NON_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_OR_EQUALS)) {
				condition.append(Constants.GREATER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_OR_EQUALS)) {
				condition.append(Constants.SMALLER_OR_EQUALS_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.SMALLER_THAN)) {
				condition.append(Constants.SMALLER_THAN_SYMBOL);
			} else if (form.getFilterOperator().equals(Constants.GREATER_THAN)) {
				condition.append(Constants.GREATER_THAN_SYMBOL);
			}
			condition.append(form.getFilterValue());
			form.setFilterCondition(condition.toString());
		}
		return form;
	}

	/**
	 * This method returns an example set object returned by Rapidminer and
	 * receives the filePath of the file that will be transformed into an
	 * exampleSet.
	 * 
	 * @param filePath
	 * @return
	 */
	public ExampleSet getExampleSetFromFilePath(String filePath) {
		ExampleSet exampleSet = null;
		try {
			Process process = Preprocessing.getExampleSetFromFileProcess(
					StringUtils.substringAfterLast(filePath, Constants.DOT_SYMBOL), filePath);
			IOContainer container;
			container = process.run();
			exampleSet = (ExampleSet) container.asList().get(0);
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exampleSet;
	}

	/**
	 * Returns the list of examples that are contained into an example set.
	 * 
	 * @param exampleSet
	 * @return
	 */
	public List<Example> getExampleListFromExampleSet(ExampleSet exampleSet) {
		List<Example> examples = Lists.newArrayList();
		for (int i = 0; i < exampleSet.getExampleTable().size(); i++) {
			examples.add(exampleSet.getExample(i));
		}
		return examples;
	}

	/**
	 * Returns all the attribute names from an example set.
	 * 
	 * @param exampleSet
	 * @return
	 */
	public List<String> getAttributeNameListFromExampleSet(Attribute[] attributes) {
		List<String> attributeSelection = Lists.newArrayList();
		for (int i = 0; i < attributes.length; i++) {
			attributeSelection.add(attributes[i].getName());
		}
		return attributeSelection;
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
	public ByteArrayOutputStream exportData(String inputPath, SortedSet<Integer> rowFilter,
			String filterCondition, List<String> attributeSelection, String outputPath) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Process process = Preprocessing.getPreprocessedDataFile(inputPath, rowFilter,
					filterCondition, attributeSelection, outputPath);
			process.run();
			FileInputStream fis = new FileInputStream(new File(outputPath));
			byte[] buf = new byte[1024];
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				// Writes len bytes from the specified byte array starting at
				// offset off to this byte array output stream.
				System.out.println("read " + readNum + " bytes,");
			}
			fis.close();
		} catch (RoisinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos;
	}
}

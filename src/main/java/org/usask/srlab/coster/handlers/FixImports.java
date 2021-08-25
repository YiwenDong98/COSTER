/**
Cloned from https://github.com/khaledkucse/org.usask.srlab.COSTERPlugin
*/
package org.usask.srlab.coster.handlers;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import org.usask.srlab.coster.config.Config;
import org.usask.srlab.coster.utils.FileUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// import org.eclipse.core.commands.AbstractHandler;
// import org.eclipse.core.commands.ExecutionEvent;
// import org.eclipse.core.commands.ExecutionException;
// import org.eclipse.core.resources.IFile;
// import org.eclipse.core.resources.IProject;
// import org.eclipse.core.resources.IResource;
// import org.eclipse.jface.dialogs.Dialog;
// import org.eclipse.jface.dialogs.IDialogConstants;
// import org.eclipse.jface.text.BadLocationException;
// import org.eclipse.jface.text.FindReplaceDocumentAdapter;
// import org.eclipse.jface.text.IDocument;
// import org.eclipse.jface.text.IRegion;
// import org.eclipse.jface.window.Window;
// import org.eclipse.swt.SWT;
// import org.eclipse.swt.layout.GridData;
// import org.eclipse.swt.layout.GridLayout;
// import org.eclipse.swt.widgets.Combo;
// import org.eclipse.swt.widgets.Composite;
// import org.eclipse.swt.widgets.Control;
// import org.eclipse.swt.widgets.Label;
// import org.eclipse.swt.widgets.Shell;
// import org.eclipse.ui.IEditorInput;
// import org.eclipse.ui.IEditorPart;
// import org.eclipse.ui.IWorkbenchWindow;
// import org.eclipse.ui.handlers.HandlerUtil;
// import org.eclipse.ui.part.FileEditorInput;
// import org.eclipse.ui.texteditor.IDocumentProvider;
// import org.eclipse.ui.texteditor.ITextEditor;
// import org.fuin.utils4j.Utils4J;

public class FixImports {
	static HashMap<String,List<String>> fqnLibraryMapping;
	static ArrayList<String> libraryNames;
	static ArrayList<String> returnedFQNs;
	static ArrayList<String> finalFQNs;
	public static ArrayList<String> selectedLibraries;

// 	@Override
// 	public Object execute(ExecutionEvent event) throws ExecutionException {
// 		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
// 		IEditorInput input = editorPart.getEditorInput();
// 		IProject project = (IProject) input.getAdapter(IProject.class);
// 		if (project == null) {
// 		      IResource resource = (IResource) input.getAdapter(IResource.class);
// 		      if (resource != null) {
// 		         project = resource.getProject();
// 		      }
// 		}		
// 		if (input instanceof FileEditorInput) {
// 		    IFile file = ((FileEditorInput) input).getFile();
// 		    try {
// 				InputStream inputStream = file.getContents();
// 			    InputStreamReader isReader = new InputStreamReader(inputStream);
// 			    BufferedReader reader = new BufferedReader(isReader);
// 			    StringBuffer sb = new StringBuffer();
// 			    String str;
// 			    while((str = reader.readLine())!= null){
// 			    	sb.append(str);
// 			    	sb.append("\n");
// 			    }
// 			    
// 			    String inputFilePath = Config.ROOT_PATH+"data"+File.separator+"repo"+File.separator+"input.java";
// 			    File inputFile = new File(inputFilePath); 		
// 	    			    		
// 	    		inputFilePath = inputFile.getAbsolutePath();
// 	    		String inputDirectory = inputFilePath.
// 	    		    substring(0,inputFilePath.lastIndexOf(File.separator))+File.separator;
// 			    
// 	    		FileUtil.getSingleTonFileUtilInst().writeToFile(inputFilePath,sb.toString());
// 			    
			    //returnedFQNs = (ArrayList<String>) completeImportStatements(inputDirectory);   
// 			    String importStmt = getImrtStmtAndLibrary();
// 			    insertText(editorPart,importStmt);
// 			    
// 			    if(libraryNames.size()>0) {
// 			    	ArrayList<ArrayList<String>> versions = getVersions();
// 				    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
// 				    ImportLibraryDialog dialog = new ImportLibraryDialog(window.getShell(),libraryNames,versions);
// 					
// 				    if (dialog.open() == Window.OK) {
// 						ArrayList<File> jarLibs = new ArrayList<>();
// 						for(String each_lib:selectedLibraries) {
// 							String lib_file_name = each_lib+".jar";
// 							String full_path = Config.ROOT_PATH+"data/jars/so/"+lib_file_name;
// 							Utils4J.addToClasspath(new File(full_path));
////							jarLibs.add(new File(full_path));
// 						}
////						AddLibrary.addLibrary(project.getName(), jarLibs);
// 					}
// 				}
			    
// 			} catch (Exception e) {
// 				e.printStackTrace();
// 			}
// 		    
// 		}
// 		return null;
// 	}
	
// 	private static ArrayList<ArrayList<String>> getVersions() {
// 		ArrayList<ArrayList<String>> versions = new ArrayList<>();
// 		String jarRepoPath = Config.ROOT_PATH+"data/jars/so/";
// 		ArrayList<String> allJars = FileUtil.getSingleTonFileUtilInst().getFileNames(new File(jarRepoPath));
// 		
// 		for(String eachLibrary:libraryNames) {
// 			ArrayList<String> each_lib_version = new ArrayList<>();
// 			for(String eachJarFiles: allJars) {
// 				if(eachJarFiles.contains(eachLibrary))
// 					each_lib_version.add(eachJarFiles);
// 			}
// 			versions.add(each_lib_version);
// 		}
// 		return versions;
// 	}
	
	private static List<String> completeImportStatements(String inputRepo) {
		String jarRepoPath = Config.ROOT_PATH+"data/jars/so/";
		String modelPath = Config.ROOT_PATH+"model/";
		String dictPath = Config.ROOT_PATH+"data/dictonary/so/";
// 		COSTER.init();
// 		COSTER.setJarRepoPath(jarRepoPath);
// 		COSTER.setRepositoryPath(inputRepo);
// 		COSTER.setModelPath(modelPath);
// 		COSTER.setContextSimilarity(Config.CONTEXT_SIMILARITY_FUNCTION);
// 		COSTER.setNameSimilarity(Config.NAME_SIMIALRITY_FUNCTION);
// 		COSTER.setReccs(Config.TOP_K);
// 		COSTER.setFqnThreshold(Config.FQN_THRESHOLD);
		fqnLibraryMapping  = FileUtil.getSingleTonFileUtilInst().getFQNLibararyMapping(dictPath);
		return null;
// 		return ImportStmtComplete.complete(dictPath);
	}
	
	private static String getImrtStmtAndLibrary() {
		StringBuffer importStmt = new StringBuffer();
		libraryNames = new ArrayList<>();
		finalFQNs = new ArrayList<>();
	    for(String eachFQN:returnedFQNs) {
	    	if(eachFQN.contains(".")) { 
	    		if(fqnLibraryMapping.containsKey(eachFQN) && !fqnLibraryMapping.get(eachFQN).equals(null)) {
	    			List<String> libraries = fqnLibraryMapping.get(eachFQN);
	    			importStmt.append("import "+eachFQN+";\n");
	    			finalFQNs.add(eachFQN);
	    			for(String eachLibrary:libraries) {
	    				if(!libraryNames.contains(eachLibrary) && !eachLibrary.equals("rt")) {
	    					libraryNames.add(eachLibrary);
	    				}
	    			}
	    		}
	    	}
	    }
	    return importStmt.toString();
	}

	private static Map<String, String> libraryMapping;

	static {
		libraryMapping = new HashMap<>();
		libraryMapping.put("android", "android-13");
		libraryMapping.put("hibernate", "hibernate-3.5.3");
		libraryMapping.put("joda-time", "joda-time-2.0");
		libraryMapping.put("gwt", "gwt-user");
		libraryMapping.put("gwt-servlet", "gwt-servlet-1.4.62");
		libraryMapping.put("commons-logging", "commons-logging-1.1");
		libraryMapping.put("javax.servlet-api", "commons-logging-1.1");
		libraryMapping.put("dom4j", "dom4j-1.4");
		libraryMapping.put("xstream", "com.thoughtworks.xstream");
		libraryMapping.put("httpclient", "httpclient-4.3.3");
		libraryMapping.put("jfxrt", "jfxrt");
		libraryMapping.put("junit", "junit-4.11");
		libraryMapping.put("commons-lang", "commons-lang3-3.1");
		libraryMapping.put("javax.faces-api", "javax.faces-api-2.0");
		libraryMapping.put("json", "json-20090211");
		libraryMapping.put("log4j", "log4j-1.2.15");
		libraryMapping.put("com.thoughtworks.xstream", "com.thoughtworks.xstream");
	}

	public static void main(String[] args) throws IOException {
        String costerStatPath = System.getenv("coster_stat_path");
        if (args.length < 1) {
        	throw new RuntimeException("Expect args for output csv");
		}
        String outputCSVDir = args[0];
		if (!Files.isDirectory(Paths.get(outputCSVDir))) {
			throw new RuntimeException("Expected output csv dir to be a dir");
		}

        if (costerStatPath != null) {
			Map<String, List<String>> fileToFQNs = new HashMap<>();
			CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new FileReader(costerStatPath));
			csvReaderBuilder.withCSVParser(new CSVParser(',', '"','\\'));
			CSVReader csvReader = csvReaderBuilder.build();
			for (String[] line : csvReader) {
				System.out.println(Arrays.toString(line));
				if (line.length != 7) {
					throw new RuntimeException("Expected csv line to be length of 7");
				}
//				System.out.println("File: " + line[0]);
//				System.out.println("Solved FQN: " + line[6]);
				if (!fileToFQNs.containsKey(line[0])) {
					fileToFQNs.put(line[0], new ArrayList<>());
				}
				fileToFQNs.get(line[0]).add(line[6]);
			}
			completeImportStatements(null);

			ArrayList<String> files = new ArrayList<>(fileToFQNs.keySet());
			Collections.sort(files);


			CSVWriter writer = new CSVWriter(new FileWriter(Paths.get(outputCSVDir, "individual_lib_stat_coster.csv").toString()), ',', '"', '\\', "\n");

			for (String file : files) {
				returnedFQNs = new ArrayList<>(fileToFQNs.get(file));
				getImrtStmtAndLibrary();
				System.out.println(file);
				System.out.println(libraryNames);
				System.out.println(finalFQNs);
				String[] csvLine = new String[libraryNames.size() + 1];
				csvLine[0] = file;
				for (int i = 0; i < libraryNames.size(); i++) {
					String libraryName = libraryNames.get(i);
					if (!libraryMapping.containsKey(libraryName)) {
						throw new RuntimeException("Mapping not found for library name: " + libraryName);
					}
					csvLine[i+1] = "jar:" + libraryMapping.get(libraryName) + ".jar:1";
				}
				writer.writeNext(csvLine);
			}
			writer.flush();
			writer.close();
			return;
        }
        throw new RuntimeException("coster_stat_path was not set");
	}
	
// 	private void insertText(IEditorPart editorPart, String importStmt) {
// 		ITextEditor editor = (ITextEditor)editorPart;
// 	    IDocumentProvider dp = editor.getDocumentProvider();
// 	    IDocument doc = dp.getDocument(editor.getEditorInput());
// 	    FindReplaceDocumentAdapter searchdoc = new FindReplaceDocumentAdapter(doc);
// 		try {
// 			String searchTOken = "package .*;";
// 			IRegion firstLine = searchdoc.find(0, searchTOken, true, false, false,true);
// 			if(firstLine == null)
// 				doc.replace(0, 0, importStmt+"\n");
// 			else {
// 				int offset = firstLine.getOffset()+firstLine.getLength()+1;
// 				doc.replace(offset, 0, "\n"+importStmt+"\n");
// 			}
// 				
// 		} catch (BadLocationException e) {
// 			e.printStackTrace();
// 		}
// 	    
// 	}
	
}

// class ImportLibraryDialog extends Dialog {
//     ArrayList<String> libraries;
//     ArrayList<ArrayList<String>> versions;
//     ArrayList<Label> labelLibraries;
//     ArrayList<Combo> comboVersions;
//     
// 	public ImportLibraryDialog(Shell parentShell,ArrayList<String> libraries,ArrayList<ArrayList<String>> versions) {
// 		super(parentShell);
//         this.libraries = libraries;
//         this.versions = versions;
//         this.labelLibraries = new ArrayList<>();
//         this.comboVersions = new ArrayList<>();
//     }
// 
//     @Override
//     protected Control createDialogArea(Composite parent) {
//         Composite container = (Composite) super.createDialogArea(parent);
//         GridLayout layout = new GridLayout(2, false);
//         layout.numColumns = 2;
//         layout.marginRight = 5;
//         layout.marginLeft = 10;
//         container.setLayout(layout);
//         
//         Label lbllibraries = new Label(container, SWT.NONE);
//         lbllibraries.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, true));
//         lbllibraries.setText("Libraries");
//         
//         Label lblversion = new Label(container, SWT.NONE);
//         lblversion.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, true));
//         lblversion.setText("Versions");
//         
//         for(int i =0; i<libraries.size();i++) {
//         	 Label each_lib_label = new Label(container, SWT.NONE);
//         	 GridData gd = new GridData(GridData.CENTER, GridData.CENTER, true, true);
//         	 gd.widthHint = 250;
//         	 each_lib_label.setLayoutData(gd);
//         	 each_lib_label.setText(libraries.get(i));
//         	 labelLibraries.add(each_lib_label);
//         	 
//         	 Combo combo = new Combo(container, SWT.DROP_DOWN);
//         	 String[] values = new String[versions.get(i).size()];
//         	 values = versions.get(i).toArray(values);
//         	 combo.setItems(values);
//         	 combo.select(0);
//         	 comboVersions.add(combo);
//         }					       
// 		
//         return container;
//     }
// 
//     //override method to use "Login" as label for the OK button
//     @Override
//     protected void createButtonsForButtonBar(Composite parent) {
//         createButton(parent, IDialogConstants.OK_ID, "Import", true);
//         createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
//     }
// 
//     @Override
//     protected void okPressed() {
//     	FixImports.selectedLibraries = new ArrayList<>();
//     	for(Combo each_lib:comboVersions) {
//     		FixImports.selectedLibraries.add(each_lib.getText());
//     	}
//     	super.okPressed();
//         
//     }
// 
// }

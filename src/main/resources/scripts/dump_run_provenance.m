
% load the execution object
[run_parent_directory,run_id] = fileparts(pwd); 
executionWorkspaceFile = [run_parent_directory '/' run_id '/' run_id '.mat'];
load(executionWorkspaceFile);

disp('Inputs:')
for input_id=executionObj.execution_input_ids
    input_object = executionObj.execution_objects(input_id{1});
    input_file_path = input_object.get('full_file_path');
    disp(input_file_path)
end

disp('Outputs:')
for output_id=executionObj.execution_output_ids
    output_object = executionObj.execution_objects(output_id{1});
    output_file_path = output_object.get('full_file_path');
    disp(output_file_path)
end

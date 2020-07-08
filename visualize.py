import numpy as np
import re
import matplotlib.pyplot as plt

# Tests performed by TFB
TESTS = ["DB", "FORTUNE", "QUERY", "UPDATE"]
# Parameters to compare various tests
PARAMS = ["Transactions", "Shortesttransaction", "Longesttransaction", "Transactionrate", "Concurrency", "Responsetime", "Throughput"]

# Helper function to process file input
def TestOrder(string):
	global TESTS
	for test in TESTS:
		if (string == "VERIFYING" + test):
			return True
	return False

# Helper function to process file input
def FilterParams(string):
	global PARAMS
	for substring in PARAMS:
		if (string.find(substring) != -1):
			return True
	return False

# Return a dictionary based on data of a particular test
def GenerateDataDict(data):
	datadict = {}
	for d in data:
		datadict[d[0]] = float(re.sub(r"[^0-9\.]", "", d[1]))
	return datadict

# Combine test order and data corresponding to the test into a dictionary
def GenerateResultDict(order, data):
	global PARAMS
	assert(len(order) * len(PARAMS) == len(data))
	result = {}
	for i in range(len(order)):
		result[order[i]] = GenerateDataDict([data[i * len(PARAMS) + j].split(":") for j in range(len(PARAMS))])
	return result

# Generates a dictionary of results based on tests performed
def Preprocess(filepath):
	with open(filepath, "r") as file:
		content = file.read()
		lines = content.split("\n")
		lines = [re.sub(r"\s+", "", line) for line in lines]
		# Get the data for every test performed from the file
		data = list(filter(lambda line: FilterParams(line), lines))
		# Get the order of tests performed
		order = filter(lambda line: TestOrder(line), lines)
		order = list(map(lambda line: line.replace("VERIFYING", ""), order))
		# Combine the data collected and order of tests to generate the result
		return GenerateResultDict(order, data)

# Fetch values in a ordered format as in PARAMS
def FormatValues(test_result):
	global PARAMS
	return [test_result[param] for param in PARAMS]

# Plot a graph based on results
def Visualize(results, files):
	global PARAMS, TESTS
	num_files = len(files)
	for test in TESTS:
		bar_width = 0.15
		index = np.arange(len(PARAMS))
		fig, ax = plt.subplots()
		reference = np.array(FormatValues(results[0][test]))
		for i in range(num_files):
			values = np.array(FormatValues(results[i][test])) / reference * 100
			ax.bar(index + i * bar_width, values, bar_width, label=files[i])
			for e, v in enumerate(values):
				ax.text(index[e] + (i - 0.5) * bar_width, float(v) + 3, int(v), color="black")
		ax.set_xlabel("PARAMETERS")
		ax.set_ylabel("PERCENTAGE (Taking first one as a reference)")
		ax.set_title(f"Performance comparision for TFB test - {test}")
		ax.set_xticks(index + bar_width * num_files / 2)
		ax.set_xticklabels(PARAMS)
		ax.legend()
		plt.show()

if __name__ == '__main__':
	# Specify the files to be used for visualizing
	# Data from the first file will be used as reference
	files = []
	results = [Preprocess(file) for file in files]
	Visualize(results, files)
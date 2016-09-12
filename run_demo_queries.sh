#!/usr/bin/env bash -l
#
# ./run_queries.sh &> run_queries.txt

xsb << END_XSB_STDIN

[rules].
[extractfacts].
[modelfacts].
[reconfacts].
[extract_queries].
[model_queries].
[recon_queries].

printall('mq5(DataName) - What are the inputs to the script?', mq5(_)).
printall('mq18(UpstreamDataName) - What data is upstream of SYNMAP_land_cover_map_variable?', mq18(_)).
printall('mq21(DataProduct, InputPortName) - List the script inputs that are upstream of a given data product D.', mq21('C4_fraction_data',_)).

END_XSB_STDIN


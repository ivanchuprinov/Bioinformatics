max=-100;
permutations=$1;
shift

for ((i = 0 ; i < permutations; i++)); do
  thisRun=$(java randomMSA $@)
  if [[ $max -lt $thisRun ]]; then
    max=${thisRun}
    echo 'new max = ' $max
  fi
   
done
echo 'final max = ' $max

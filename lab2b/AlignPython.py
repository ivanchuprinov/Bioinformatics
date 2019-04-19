import time

# 0: constants
seqFileName = "seq.txt"
match = 5
miss = -3

# 1: confirm input

goodInput = False
while(not goodInput):
    SOURCE = input("\nSOURCE: ")

    for char in SOURCE:
        if(char == 'T' or char == 'A' or char == 'G' or char == 'C'):
            goodInput = True
        else:
            print("Invalid input. Try again!")
            goodInput = False
            break

# 2: connect to the file
seqFile = open(seqFileName)

# 3: defines values we need to present our result
maxScoreCur = float("-inf")
bestLineID = ""
bestSequence = ""
maxScoreIndx = 0

# Timestamp 1 (before searching)
ts1 = time.time()

# 4: go through each line
for line in seqFile:

    # 5: separate line id and the sequence
    lineID = line[0:8]
    sequence = line[10:len(line)]
    
    # 6: iterate through each letter of the sequence
    seqOffset = 0
    while(seqOffset < len(sequence)):
        scoreCur = 0

        # 7: compare each letter in sequence to each letter in source and record matches/mismatches
        sourceOffset = 0
        while(seqOffset + sourceOffset < len(sequence) and sourceOffset < len(SOURCE)):
            if(sequence[seqOffset + sourceOffset] == SOURCE[sourceOffset]):
                scoreCur += 5
            else:
                scoreCur -= 3
            sourceOffset += 1
        
        # 8: punish leftover letters in source (in case it goes past the sequence length)
        scoreCur -= (3 * (len(SOURCE) - sourceOffset))

        # 9: store the data about the best-scoring match
        if(scoreCur >= maxScoreCur):
            maxScoreCur = scoreCur
            maxScoreIndx = seqOffset
            bestLineID = lineID
            bestSequence = sequence
        seqOffset += 1

# Timestamp 2 (after searching)
ts2 = time.time()

# 10: Create the string containing the lines that connect the matching nucleotides
matches = " " * maxScoreIndx
i = 0
while(i < len(SOURCE) and i < len(bestSequence)):
    if(SOURCE[i] == bestSequence[i+maxScoreIndx]):
        matches += '|'
    else:
        matches += ' '
    i += 1


# 11: Produce output
print("Highest score: " + str(maxScoreCur))
print("Best match line: " + bestLineID)
print(" " * maxScoreIndx + SOURCE)
print(matches)
print(bestSequence)

# Calculate and output lookup time
ts = float('%.3f'%(ts2 - ts1))
print("Search time: " + str(ts) + " seconds\n")
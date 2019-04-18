
# 1: confirm input

goodInput = False
while(not goodInput):
    SOURCE = raw_input("SOURCE: ")

    for char in SOURCE:
        if(char == 'T' or char == 'A' or char == 'G' or char == 'C'):
            goodInput = True
        else:
            print("Invalid input. Try again!")
            goodInput = False
            break

# 2: connect to file, assign weights
seqFile = open("seq.txt")
match = 5
miss = -3
perfectScore = len(SOURCE) * match

# 3: go through each line
for line in seqFile:
    
    # 4: calculate score
    maxScoreCur = 0

    i = 0
    while(maxScoreCur != perfectScore and i < len(line)):
        scoreCur = 0
        j = 0
        while(i+j < len(line)):
            

        i += 1
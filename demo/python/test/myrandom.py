import random

#answer=random.randrange(100)
#answer=25

answer=random.randint(0,100);


min=0
max=100

guess=-1

while guess!=answer:
	print("The answer is between {} and {}".format(min,max))
	guess=(int)(input("input your guess\n"))
	if(guess<answer and guess>min):
		min=guess
	elif(guess>answer and guess<max):
		max=guess

print("The answer is {}, you got it!".format(answer))
#!/bin/sh

echo "\033[34mRunning maven package\033[0m"
RUN_CHECK_CMD="mvn clean package -DurlPath=''"
RUN_TESTS_OUTPUT=`${RUN_CHECK_CMD}`

if [ $? -ne 0 ]
then
  echo "\033[34mCan't commit! You've broken build!!!\033[0m"
  exit 1
fi

# echo "\033[34mRunning karma tests\033[0m"
# RUN_CHECK_CMD='karma start --single-run --browsers PhantomJS'
# RUN_TESTS_OUTPUT=`${RUN_CHECK_CMD}`
#
# if [ $? -ne 0 ]
# then
#   echo "\033[34mCan't commit! You've broken karma!!!\033[0m"
#   exit 1
# fi

echo "\033[34mAll checks passed. You didn't break anything. Congrats!\n\033[0m"
exit 0

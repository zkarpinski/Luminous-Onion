##
## Grype Example Script
## This script will download and install Grype, run a scan and upload the source/findings to the designated host.
##

testHost="$(hostname).local"
testPort="8081"
testImage="webgoat/webgoat:latest"
testJsonOutput="webgoat-grype.json"
## EDIT THESE
testProductID=1 # CHANGE TO A VALID ProductID

# Download & install Grype
curl -sSfL https://raw.githubusercontent.com/anchore/grype/main/install.sh | sh -s -- -b /usr/local/bin

# Pull latest webgoat docker image
docker pull $testImage

grype $testImage -o json --file $testJsonOutput

echo "Posting ${testJsonOutput} results to " $testHost:$testPort/upload

curl \
  --header "Content-Type: multipart/form-data" \
  --request POST \
  -F productId=$testProductID \
  -F sourceTool="ANCORE_GRYPE" \
  -F label="Grype example" \
  -F file=@$testJsonOutput \
  $testHost:$testPort/upload

##
## Docker Scout Example Script
## This script will download and install Docker Scout, run a scan and upload the source/findings to the designated host.
##

testHost="$(hostname).local"
testPort="8081"
testImage="webgoat/webgoat:latest"
testJsonOutput="webgoat-dockerscout.sarif"
## EDIT THESE
testProductID=1 # CHANGE TO A VALID ProductID

# Download & install docker-scout cli
curl -sSfL https://raw.githubusercontent.com/docker/scout-cli/main/install.sh | sh -s --

# Pull latest webgoat docker image
docker pull $testImage

docker scout cves $testImage --format sarif --output $testJsonOutput

echo "Posting ${testJsonOutput} results to " $testHost:$testPort/upload

curl \
  --header "Content-Type: multipart/form-data" \
  --request POST \
  -F productId=$testProductID \
  -F sourceTool="DOCKER_SCOUT" \
  -F label="Docker Scout example" \
  -F file=@$testJsonOutput \
  $testHost:$testPort/upload

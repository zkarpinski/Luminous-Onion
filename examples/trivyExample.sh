##
## Trivy Example Script
## This script will download and install trivy, run a scan and upload the source/findings to the designated host.
##

testHost="$(hostname).local"
testPort="8081"
testImage="webgoat/webgoat:latest"
testJsonOutput="webgoat-trivy.json"
## Values that may change
testProjectID=1

# Download & install trivy
curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin

# Pull latest webgoat docker image
docker pull $testImage

trivy image $testImage --scanners vuln -f json -o $testJsonOutput

echo "Connecting to" $testHost

curl --header "Content-Type: multipart/form-data" \
  --request POST \
  -F productId=$testProjectID \
  -F sourceTool="AQUA_TRIVY" \
  -F file=@$testJsonOutput \
  $testHost:$testPort/upload

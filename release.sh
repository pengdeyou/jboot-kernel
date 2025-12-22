#!/bin/bash

# JBoot Kernel Release Script for Sonatype Central Repository
# Usage: 
#   ./release.sh snapshot    - Deploy SNAPSHOT version
#   ./release.sh release     - Deploy RELEASE version (requires GPG signing)
#   ./release.sh help        - Show this help

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to show help
show_help() {
    echo "JBoot Kernel Release Script"
    echo ""
    echo "Usage: $0 [command]"
    echo ""
    echo "Commands:"
    echo "  snapshot  Deploy SNAPSHOT version to Sonatype repository"
    echo "  release   Deploy RELEASE version to Sonatype repository"
    echo "  help      Show this help message"
    echo ""
    echo "Prerequisites:"
    echo "  1. Configure ~/.m2/settings.xml with Sonatype credentials"
    echo "  2. Set up GPG keys for release deployment"
    echo "  3. Ensure the project version is set correctly in pom.xml"
    echo ""
    echo "Examples:"
    echo "  $0 snapshot   # Deploy 4.7.1-SNAPSHOT"
    echo "  $0 release    # Deploy 4.7.1"
}

# Function to check prerequisites
check_prerequisites() {
    print_info "Checking prerequisites..."
    
    # Check if Maven is installed
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed or not in PATH"
        exit 1
    fi
    
    # Check if GPG is installed (only for release)
    if [ "$1" = "release" ]; then
        if ! command -v gpg &> /dev/null; then
            print_error "GPG is not installed or not in PATH (required for release)"
            exit 1
        fi
    fi
    
    # Check if settings.xml exists
    if [ ! -f "$HOME/.m2/settings.xml" ]; then
        print_warn "~/.m2/settings.xml not found. Using project settings-release.xml as reference."
        print_warn "Please configure your Maven settings with Sonatype credentials."
    fi
    
    # Check if we're in the right directory
    if [ ! -f "pom.xml" ]; then
        print_error "pom.xml not found. Please run this script from the project root directory."
        exit 1
    fi
    
    print_info "Prerequisites check passed."
}

# Function to get current version from pom.xml
get_current_version() {
    version=$(grep -E '<revision>[^<]+</revision>' pom.xml | sed -E 's/.*<revision>([^<]+)<\/revision>.*/\1/')
    echo $version
}

# Function to deploy SNAPSHOT version
deploy_snapshot() {
    local version=$(get_current_version)
    
    if [[ ! $version == *"-SNAPSHOT"* ]]; then
        print_error "Current version ($version) is not a SNAPSHOT version."
        print_error "Please set version to end with '-SNAPSHOT' for snapshot deployment."
        exit 1
    fi
    
    print_info "Deploying SNAPSHOT version: $version"
    print_info "This will deploy to Sonatype snapshot repository."
    
    read -p "Continue? (y/N): " -n 1 -r
    echo
    
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        print_warn "Deployment cancelled."
        exit 0
    fi
    
    # Build and deploy
    mvn clean deploy -DskipTests -Drevision=$version
    
    if [ $? -eq 0 ]; then
        print_info "SNAPSHOT deployment completed successfully!"
        print_info "Check: https://ossrh-staging-api.central.sonatype.com/content/repositories/snapshots/"
    else
        print_error "SNAPSHOT deployment failed!"
        exit 1
    fi
}

# Function to deploy RELEASE version
deploy_release() {
    local version=$(get_current_version)
    
    if [[ $version == *"-SNAPSHOT"* ]]; then
        print_error "Current version ($version) is a SNAPSHOT version."
        print_error "Please remove '-SNAPSHOT' suffix for release deployment."
        exit 1
    fi
    
    print_info "Deploying RELEASE version: $version"
    print_info "This will deploy to Sonatype staging repository with GPG signing."
    
    read -p "Continue? (y/N): " -n 1 -r
    echo
    
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        print_warn "Deployment cancelled."
        exit 0
    fi
    
    # Check GPG key availability
    print_info "Checking GPG key availability..."
    if ! gpg --list-secret-keys | grep -q "sec"; then
        print_error "No GPG secret keys found. Please generate a GPG key first:"
        print_error "  gpg --gen-key"
        exit 1
    fi
    
    # Build and deploy with sonatype-release profile
    mvn clean deploy -P sonatype-release -DskipTests -Drevision=$version
    
    if [ $? -eq 0 ]; then
        print_info "Release deployment completed successfully!"
        print_info "The artifacts are now in the Sonatype staging repository."
        print_info "Check: https://ossrh-staging-api.central.sonatype.com/"
        print_info ""
        print_info "Next steps:"
        print_info "1. Log in to Sonatype Nexus: https://ossrh-staging-api.central.sonatype.com/"
        print_info "2. Close the staging repository"
        print_info "3. Release the staging repository to Maven Central"
        print_info ""
        print_info "Or use Maven command to automatically release:"
        print_info "  mvn nexus-staging:release -P release -Drevision=$version"
    else
        print_error "Release deployment failed!"
        exit 1
    fi
}

# Function to automatically release staged repository
auto_release() {
    local version=$(get_current_version)
    
    if [[ $version == *"-SNAPSHOT"* ]]; then
        print_error "Cannot auto-release SNAPSHOT version."
        exit 1
    fi
    
    print_info "Auto-releasing staged repository for version: $version"
    
    mvn nexus-staging:release -P sonatype-release -Drevision=$version
    
    if [ $? -eq 0 ]; then
        print_info "Auto-release completed successfully!"
        print_info "The artifacts will be available in Maven Central after synchronization (usually 10-30 minutes)."
    else
        print_error "Auto-release failed!"
        exit 1
    fi
}

# Main script logic
case "${1:-}" in
    "snapshot")
        check_prerequisites "snapshot"
        deploy_snapshot
        ;;
    "release")
        check_prerequisites "release"
        deploy_release
        ;;
    "auto-release")
        check_prerequisites "release"
        auto_release
        ;;
    "help"|"-h"|"--help")
        show_help
        ;;
    "")
        print_error "No command specified."
        show_help
        exit 1
        ;;
    *)
        print_error "Unknown command: $1"
        show_help
        exit 1
        ;;
esac
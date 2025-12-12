#!/usr/bin/env python3
"""
Spring Boot Upgrade Assessment Script
Analyzes current project to determine upgrade complexity
"""

import os
import re
import json
from pathlib import Path

def find_pom_files(root_dir):
    """Find all pom.xml files recursively"""
    pom_files = []
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file == 'pom.xml':
                pom_files.append(os.path.join(root, file))
    return pom_files

def extract_spring_version(pom_content):
    """Extract Spring Boot version from pom.xml"""
    patterns = [
        r'<spring\.boot\.version>([^<]+)</spring\.boot\.version>',
        r'<spring\.version>([^<]+)</spring\.version>',
        r'<spring-cloud\.version>([^<]+)</spring-cloud\.version>'
    ]
    
    versions = {}
    for pattern in patterns:
        match = re.search(pattern, pom_content)
        if match:
            key = pattern.split('\\.')[1].replace('\\', '')
            versions[key] = match.group(1)
    
    return versions

def find_javax_imports(java_files):
    """Find all javax imports in Java files"""
    javax_imports = set()
    javax_count = 0
    
    for java_file in java_files:
        try:
            with open(java_file, 'r', encoding='utf-8') as f:
                content = f.read()
                imports = re.findall(r'import\s+javax\.[^;]+;', content)
                for imp in imports:
                    javax_imports.add(imp)
                    javax_count += 1
        except Exception as e:
            print(f"Error reading {java_file}: {e}")
    
    return javax_imports, javax_count

def find_java_files(root_dir):
    """Find all Java files recursively"""
    java_files = []
    for root, dirs, files in os.walk(root_dir):
        # Skip .git and target directories
        dirs[:] = [d for d in dirs if not d.startswith('.') and d != 'target']
        
        for file in files:
            if file.endswith('.java'):
                java_files.append(os.path.join(root, file))
    
    return java_files

def assess_upgrade_complexity(root_dir):
    """Main assessment function"""
    print("🔍 Starting Spring Boot Upgrade Assessment...")
    
    # Analyze pom files
    pom_files = find_pom_files(root_dir)
    print(f"📁 Found {len(pom_files)} pom.xml files")
    
    current_versions = {}
    for pom_file in pom_files:
        with open(pom_file, 'r', encoding='utf-8') as f:
            content = f.read()
            versions = extract_spring_version(content)
            if versions:
                current_versions.update(versions)
    
    print(f"📦 Current versions: {current_versions}")
    
    # Analyze Java files for javax usage
    java_files = find_java_files(root_dir)
    print(f"📝 Found {len(java_files)} Java files")
    
    javax_imports, javax_count = find_javax_imports(java_files)
    print(f"🔄 Found {javax_count} javax imports across {len(javax_imports)} unique packages")
    
    if javax_imports:
        print("\n📋 javax imports found:")
        for imp in sorted(javax_imports):
            print(f"  {imp}")
    
    # Assessment results
    assessment = {
        'current_versions': current_versions,
        'pom_files_count': len(pom_files),
        'java_files_count': len(java_files),
        'javax_imports_count': javax_count,
        'unique_javax_packages': len(javax_imports),
        'javax_imports': list(javax_imports)
    }
    
    # Determine complexity
    if javax_count == 0:
        complexity = "Low"
    elif javax_count < 50:
        complexity = "Medium"
    elif javax_count < 200:
        complexity = "High"
    else:
        complexity = "Very High"
    
    print(f"\n🎯 Upgrade Complexity: {complexity}")
    
    return assessment

if __name__ == "__main__":
    root_dir = "."
    assessment = assess_upgrade_complexity(root_dir)
    
    # Save assessment results
    with open("upgrade_assessment.json", "w") as f:
        json.dump(assessment, f, indent=2)
    
    print("\n💾 Assessment saved to upgrade_assessment.json")
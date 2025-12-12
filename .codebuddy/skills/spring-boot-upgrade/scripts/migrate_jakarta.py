#!/usr/bin/env python3
"""
Automated javax to jakarta migration script
"""

import os
import re
import json

# Migration mapping from javax to jakarta
MIGRATION_MAP = {
    # XML Bind
    'javax.xml.bind': 'jakarta.xml.bind',
    
    # Servlet
    'javax.servlet': 'jakarta.servlet',
    'javax.servlet.http': 'jakarta.servlet.http',
    
    # Persistence
    'javax.persistence': 'jakarta.persistence',
    
    # Activation
    'javax.activation': 'jakarta.activation',
    
    # Annotation
    'javax.annotation': 'jakarta.annotation',
    'javax.annotation.security': 'jakarta.annotation.security',
    
    # JMS
    'javax.jms': 'jakarta.jms',
    
    # Transaction
    'javax.transaction': 'jakarta.transaction',
    
    # Validation
    'javax.validation': 'jakarta.validation',
    'javax.validation.constraints': 'jakarta.validation.constraints',
    
    # Enterprise
    'javax.enterprise': 'jakarta.enterprise',
    'javax.enterprise.context': 'jakarta.enterprise.context',
    'javax.enterprise.inject': 'jakarta.enterprise.inject',
    
    # EJB
    'javax.ejb': 'jakarta.ejb',
    
    # JAX-RS
    'javax.ws.rs': 'jakarta.ws.rs',
    
    # JAX-WS
    'javax.xml.ws': 'jakarta.xml.ws',
    
    # JTA
    'javax.transaction.xa': 'jakarta.transaction.xa'
}

# Packages that DON'T need migration (JDK standard library)
NO_MIGRATION_PACKAGES = [
    'javax.crypto',
    'javax.net.ssl',
    'javax.security',
    'javax.sql',
    'javax.management',
    'javax.naming',
    'javax.rmi',
    'javax.xml.parsers',  # This might need migration in some cases
    'javax.xml.xpath',    # This might need migration in some cases
]

def should_migrate(import_line):
    """Check if the import should be migrated to jakarta"""
    for javax_pkg in MIGRATION_MAP.keys():
        if f'import {javax_pkg}.' in import_line:
            return True
    
    for no_migrate_pkg in NO_MIGRATION_PACKAGES:
        if f'import {no_migrate_pkg}.' in import_line:
            return False
            
    return None

def migrate_java_file(file_path):
    """Migrate imports in a single Java file"""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        original_content = content
        changes_made = []
        
        # Apply migration mapping
        for javax_pkg, jakarta_pkg in MIGRATION_MAP.items():
            # Replace import statements
            pattern = rf'import\s+({re.escape(javax_pkg)}\.[^;]+;)'
            matches = re.findall(pattern, content)
            if matches:
                for match in matches:
                    old_import = f'import {match}'
                    new_import = old_import.replace(javax_pkg, jakarta_pkg)
                    content = content.replace(old_import, new_import)
                    changes_made.append((old_import, new_import))
        
        # Only write file if changes were made
        if content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            return changes_made
        else:
            return []
            
    except Exception as e:
        print(f"Error migrating {file_path}: {e}")
        return []

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

def migrate_project(root_dir="."):
    """Migrate entire project"""
    print("🔄 Starting javax to jakarta migration...")
    
    java_files = find_java_files(root_dir)
    print(f"📝 Found {len(java_files)} Java files to check")
    
    total_changes = 0
    files_changed = 0
    
    for java_file in java_files:
        changes = migrate_java_file(java_file)
        if changes:
            files_changed += 1
            total_changes += len(changes)
            print(f"✅ Migrated {java_file} ({len(changes)} changes)")
            for old_imp, new_imp in changes:
                print(f"    {old_imp} → {new_imp}")
    
    print(f"\n🎉 Migration complete!")
    print(f"📊 Files changed: {files_changed}")
    print(f"🔄 Total imports migrated: {total_changes}")
    
    return {
        'files_processed': len(java_files),
        'files_changed': files_changed,
        'total_changes': total_changes
    }

def check_migration_needed(root_dir="."):
    """Check if migration is needed"""
    java_files = find_java_files(root_dir)
    javax_count = 0
    
    for java_file in java_files:
        try:
            with open(java_file, 'r', encoding='utf-8') as f:
                content = f.read()
                
            for javax_pkg in MIGRATION_MAP.keys():
                pattern = rf'import\s+{re.escape(javax_pkg)}\.[^;]+;'
                matches = re.findall(pattern, content)
                javax_count += len(matches)
                
        except Exception:
            continue
    
    print(f"🔍 Found {javax_count} javax imports that need migration")
    return javax_count > 0

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) > 1 and sys.argv[1] == "--check":
        check_migration_needed()
    else:
        migrate_project()
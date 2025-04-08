#!/usr/bin/env bash
set -e

echo "=========================================="
echo "Setting up pre-commit hooks for development"
echo "=========================================="

# Check for Python installation
if ! command -v python3 &>/dev/null; then
    echo "Error: Python 3 is required but not found."
    echo "Please install Python 3.x and try again."
    exit 1
fi

# Create a virtual environment directory if it doesn't exist
VENV_DIR=".venv"
if [ ! -d "$VENV_DIR" ]; then
    echo "Creating a Python virtual environment..."
    python3 -m venv "$VENV_DIR"
else
    echo "Using existing virtual environment."
fi

# Activate the virtual environment
echo "Activating virtual environment..."
source "$VENV_DIR/bin/activate"

# Upgrade pip in the virtual environment
echo "Upgrading pip..."
pip install --upgrade pip

# Install pre-commit in the virtual environment
echo "Installing pre-commit..."
pip install --upgrade pre-commit

# Ensure we're in the repo root (where .pre-commit-config.yaml is located)
if [ ! -f ".pre-commit-config.yaml" ]; then
    echo "Error: .pre-commit-config.yaml not found in the current directory."
    echo "Please run this script from the root of the repository."
    exit 1
fi

# Install the pre-commit hooks - using absolute path to the pre-commit in the virtualenv
echo "Installing pre-commit hooks..."
PRE_COMMIT_PATH="$PWD/$VENV_DIR/bin/pre-commit"

# Create a file called .git/hooks/pre-commit pointing to our virtualenv pre-commit
mkdir -p .git/hooks

# Create a pre-commit hook script that will invoke the pre-commit from the virtual environment
cat > .git/hooks/pre-commit << EOF
#!/usr/bin/env bash
# This file was created by setup.sh to run pre-commit from a virtual environment

# Run pre-commit from the virtual environment
$PRE_COMMIT_PATH run --hook-stage pre-commit
EOF

# Make the hook executable
chmod +x .git/hooks/pre-commit

# Ensure Gradle wrapper is executable
if [ -f "./gradlew" ]; then
    echo "Making Gradle wrapper executable..."
    chmod +x ./gradlew
else
    echo "Warning: Gradle wrapper (gradlew) not found."
    echo "Make sure it exists and is executable before committing."
fi

# Validate the setup by running pre-commit on all files
echo "Validating pre-commit setup..."
if "$PRE_COMMIT_PATH" run --all-files; then
    echo "=========================================="
    echo "Setup complete! Pre-commit hooks are now active."
    echo "These hooks will run automatically on each commit."
    echo ""
    echo "To run pre-commit manually:"
    echo "  $PRE_COMMIT_PATH run --all-files"
    echo ""
    echo "You can add this as an alias in your shell profile for convenience."
    echo "=========================================="
else
    echo "=========================================="
    echo "Setup complete, but some pre-commit checks failed."
    echo "You'll need to fix these issues before you can commit."
    echo "=========================================="
    exit 1
fi
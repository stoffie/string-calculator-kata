task default: %w[test]

task :test do
	sh 'mvn package -DskipTests=true'
	sh 'rspec'
end

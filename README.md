## DAG Executor (Experimental)

This is an attempt to create a Java based DAG executor, similar to Apache Airflow, which could be used to run and easily manage business logic based workflows. 
Contrary to how Airflow runs in a distributed environment with multiple worker node, this executor will run the tasks in the topographical order on a single node in a multi-threaded manner.

As Spring is one of the most popular web framework in Java, it is intended to seamlessly integrate with it.
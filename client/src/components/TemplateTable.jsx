import { Link } from "react-router-dom";

const TemplateTable = ({ templates }) => {
    return (
        <>
    
            <table className="table table-striped">
                <thead>
                    <tr>
                        <th className="col-2">Template Name</th>
                        <th className="col-2">Template Description</th>
                        <th className="col-2">Category</th>
                        <th className="col-4">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {templates.map(template => {
                        // console.log(template)
                        return (
                            <tr key={template.templateId}>
                                <td>{template.templateName}</td>
                                <td>{template.templateDescription}</td>
                                <td>{template.templateTripType.tripTypeName}</td>
                                <td>
                                    {/* todo implement routes for buttons */}
                                    <Link to={`/template/${template.templateId}`} className="btn btn-primary me-2 mb-2">View</Link>
                                    <Link to={`/template/edit/${template.templateId}`} className="btn btn-warning">Edit</Link>
                                    <Link to={`/template/delete/${template.templateId}`} className="btn btn-danger">Delete</Link>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        </>
    )
}

export default TemplateTable;